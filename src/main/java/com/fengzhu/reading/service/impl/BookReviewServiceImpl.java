package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.BookReviewConverter;
import com.fengzhu.reading.repository.BookReviewRepository;
import com.fengzhu.reading.dataObject.BookReview;
import com.fengzhu.reading.dto.BookReviewDTO;
import com.fengzhu.reading.service.BookReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Override
    public long addNewBookReview(BookReviewDTO bookReviewDTO) {
        BookReview bookReview = BookReviewConverter.convertToBookReview(bookReviewDTO);
        bookReview = bookReviewRepository.save(bookReview);
        return bookReview.getBookId();
    }

    @Override
    public Page<BookReviewDTO> getBookReviewDTOListByBookId(long bookId, Pageable pageable) {
        Page<BookReview> bookReviewPage = bookReviewRepository.findByBookId(bookId, pageable);
        if (CollectionUtils.isEmpty(bookReviewPage.getContent())) {
            return Page.empty();
        }
        List<BookReviewDTO> bookReviewDTOList = bookReviewPage.getContent().stream()
                .map(BookReviewConverter::convertToBookReviewDTO)
                .toList();
        return new PageImpl<>(bookReviewDTOList, pageable, bookReviewPage.getTotalElements());
    }
}
