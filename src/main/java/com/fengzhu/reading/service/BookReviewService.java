package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.BookReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookReviewService {

    long addNewBookReview(BookReviewDTO bookReviewDTO);

    Page<BookReviewDTO> getBookReviewDTOListByBookId(long bookId, Pageable pageable);

}
