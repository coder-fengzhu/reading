package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.BookConverter;
import com.fengzhu.reading.converter.TagConverter;
import com.fengzhu.reading.dataObject.Book;
import com.fengzhu.reading.dataObject.BookES;
import com.fengzhu.reading.dataObject.BookTag;
import com.fengzhu.reading.dataObject.Tag;
import com.fengzhu.reading.dto.BookDTO;
import com.fengzhu.reading.dto.BookRankDTO;
import com.fengzhu.reading.dto.TagDTO;
import com.fengzhu.reading.repository.BookESRepository;
import com.fengzhu.reading.repository.BookRepository;
import com.fengzhu.reading.repository.BookTagRepository;
import com.fengzhu.reading.repository.TagRepository;
import com.fengzhu.reading.service.BookService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.fengzhu.reading.Constants.BOOK_RANK_KEY;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookTagRepository bookTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BookESRepository bookESRepository;


    @Override
    @Transactional
    public BookDTO addNewBook(BookDTO bookDTO) {
        Book book = BookConverter.convertToBook(bookDTO);
        book = bookRepository.save(book);

        BookDTO updatedBookDTO = BookConverter.converToBookDTO(book);
        if (!CollectionUtils.isEmpty(bookDTO.getTagDTOList())) {
            List<TagDTO> updatedTagDTOs = saveBookTags(bookDTO.getTagDTOList(), book.getId());
            updatedBookDTO.setTagDTOList(updatedTagDTOs);
        }
        return updatedBookDTO;
    }

    @Override
    @Transactional
    public BookDTO updateBook(BookDTO bookDTO) {
        if (bookDTO.getId() == 0L) {
            throw new IllegalArgumentException("book id is null");
        }

        long bookId = bookDTO.getId();
        Book originalBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("book id not exist, " + bookId));

        if (StringUtils.isNotEmpty(bookDTO.getBookName())) {
            originalBook.setBookName(bookDTO.getBookName());
        }
        if (StringUtils.isNotEmpty(bookDTO.getAuthor())) {
            originalBook.setAuthor(bookDTO.getAuthor());
        }
        if (StringUtils.isNotBlank(bookDTO.getIsbn())) {
            originalBook.setIsbn(bookDTO.getIsbn());
        }
        if (StringUtils.isNotBlank(bookDTO.getIntroduction())) {
            originalBook.setIntroduction(bookDTO.getIntroduction());
        }
        if (bookDTO.getPublishDate() != 0L) {
            originalBook.setPublishDate(bookDTO.getPublishDate());
        }

        Book book = bookRepository.save(originalBook);
        BookDTO updatedBookDTO = BookConverter.converToBookDTO(book);
        if (!CollectionUtils.isEmpty(bookDTO.getTagDTOList())) {
            List<TagDTO> updatedTagDTOS = saveBookTags(bookDTO.getTagDTOList(), bookId);
            updatedBookDTO.setTagDTOList(updatedTagDTOS);
        }

        return updatedBookDTO;
    }

    @Override
    public Page<BookDTO> lookupBooks(PageRequest pageRequest, Long tagId) {
        if (tagId == null) {
            Page<Book> bookPage = bookRepository.findAll(pageRequest);
            return bookPage.map(BookConverter::converToBookDTO);
        }

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("tagId:" + tagId + " not found"));

        long tagGroupId = tag.getTagGroupId();
        long tagValue = tag.getTagValue();

        Page<BookTag> bookTagPage = bookTagRepository.findByTag(tagGroupId, tagValue, pageRequest);
        if (CollectionUtils.isEmpty(bookTagPage.getContent())) {
            return Page.empty();
        }

        return bookTagPage
                .map(bookTag -> BookConverter.converToBookDTO(bookRepository.findById(bookTag.getBookId()).orElse(null)));

    }

    @Override
    public BookDTO getBookDTOById(Long bookId) {
        redisTemplate.opsForZSet().incrementScore(BOOK_RANK_KEY, String.valueOf(bookId), 1);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("bookId不存在"));
        BookDTO bookDTO = BookConverter.converToBookDTO(book);
        List<TagDTO> tagDTOList = getTagsByBookId(bookId);
        bookDTO.setTagDTOList(tagDTOList);
        return bookDTO;
    }

    @Override
    public List<TagDTO> getTagsByBookId(Long bookId) {
        List<BookTag> bookTagList = bookTagRepository.findByBookId(bookId);
        List<TagDTO> allTagDTOList = Lists.newArrayList();
        for (BookTag bookTag : bookTagList) {
            long tagGroupId = bookTag.getTagGroupId();
            List<Tag> tagList = tagRepository.findByTagGroupId(tagGroupId);
            List<TagDTO> containsTagDTOList = tagList.stream()
                    .filter(tag -> (tag.getTagValue() & bookTag.getTagValue()) == tag.getTagValue())
                    .filter(Objects::nonNull)
                    .map(TagConverter::convertToTagDTO)
                    .toList();
            if (!CollectionUtils.isEmpty(containsTagDTOList)) {
                allTagDTOList.addAll(containsTagDTOList);
            }
        }
        return allTagDTOList;
    }

    @Override
    public List<BookRankDTO> getBookRank(int size) {
        Set<ZSetOperations.TypedTuple<String>> rank = redisTemplate.opsForZSet().reverseRangeWithScores(BOOK_RANK_KEY, 0, size);
        List<BookRankDTO> bookRankDTOList = Lists.newArrayList();
        int order = 0;
        for (ZSetOperations.TypedTuple<String> tuple : rank) {
            long bookId = Long.valueOf(tuple.getValue());
            Book book = bookRepository.findById(bookId).orElse(null);
            BookRankDTO bookRankDTO = BookRankDTO.builder()
                    .bookId(bookId)
                    .bookName(book == null ? "" : book.getBookName())
                    .hit(tuple.getScore().longValue())
                    .order(++order).build();

            bookRankDTOList.add(bookRankDTO);
        }
        return bookRankDTOList;
    }

    @Override
    public Page<BookDTO> searchBooks(String keyword) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookES> bookESPage = bookESRepository.findByBookNameOrIntroductionLike(keyword, keyword, pageable);
        if (CollectionUtils.isEmpty(bookESPage.getContent())) {
            return Page.empty();
        }

        List<BookDTO> bookDTOList = bookESPage.getContent().stream().map(BookConverter::converToBookDTO).toList();
        return new PageImpl<>(bookDTOList, pageable, bookESPage.getTotalElements());
    }

    private List<TagDTO> saveBookTags(List<TagDTO> tagDTOList, long bookId) {
        Map<Long, List<TagDTO>> tagGroupMap = tagDTOList.stream()
                .collect(Collectors.groupingBy(TagDTO::getTagGroupId));

        bookTagRepository.deleteByBookId(bookId);
        List<BookTag> bookTagList = new ArrayList();
        for (Map.Entry<Long, List<TagDTO>> entry : tagGroupMap.entrySet()) {
            List<TagDTO> tagDTOS = entry.getValue();
            long tagValueInGroup = getTagValue(tagDTOS);

            BookTag bookTag = BookTag.builder()
                    .bookId(bookId)
                    .tagGroupId(entry.getKey())
                    .tagValue(tagValueInGroup)
                    .build();

            bookTagList.add(bookTag);
        }

        bookTagRepository.saveAll(bookTagList);
        List<Long> tagIds = tagDTOList.stream().map(tag -> tag.getId()).collect(Collectors.toList());
        List<Tag> updatedTags = tagRepository.findByIdIn(tagIds);
        return updatedTags.stream()
                .map(TagConverter::convertToTagDTO)
                .collect(Collectors.toList());
    }

    private long getTagValue(List<TagDTO> tagDTOS) {
        long tagValueInGroup = 0;
        for (TagDTO tagDTO : tagDTOS) {
            tagValueInGroup = tagValueInGroup | tagDTO.getTagValue();
        }
        return tagValueInGroup;
    }

}
