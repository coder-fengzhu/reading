package com.fengzhu.reading.validator;

import com.fengzhu.reading.dto.BookReviewDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class BookReviewValidator {

    public void validateAddNewBookReview(BookReviewDTO bookReviewDTO) {
        if (bookReviewDTO.getBookId() == 0L) {
            throw new IllegalArgumentException("bookId为空");
        }
        if (bookReviewDTO.getUserId() == 0L) {
            throw new IllegalArgumentException("userId为空");
        }
        if (StringUtils.isEmpty(bookReviewDTO.getReviewContent())) {
            throw new IllegalArgumentException("书评内容为空");
        }
    }
}
