package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.BookDTO;
import com.fengzhu.reading.dto.BookRankDTO;
import com.fengzhu.reading.dto.TagDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookService {

    BookDTO addNewBook(BookDTO bookDTO);

    BookDTO updateBook(BookDTO bookDTO);

    Page<BookDTO> lookupBooks(PageRequest pageRequest, Long tagId);

    BookDTO getBookDTOById(Long bookId);

    List<TagDTO> getTagsByBookId(Long bookId);

    List<BookRankDTO> getBookRank(int size);

    Page<BookDTO> searchBooks(String keyword);
}
