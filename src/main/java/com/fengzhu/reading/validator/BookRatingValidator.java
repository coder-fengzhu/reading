package com.fengzhu.reading.validator;

import com.fengzhu.reading.dto.BookRatingDTO;
import org.springframework.stereotype.Component;

@Component
public class BookRatingValidator {

    public void validateAddNewBookRating(BookRatingDTO bookRatingDTO) {
        if (bookRatingDTO.getScore() == 0) {
            throw new IllegalArgumentException("score为空");
        }
        if (bookRatingDTO.getUserId() == 0L) {
            throw new IllegalArgumentException("userId为空");
        }
        if (bookRatingDTO.getBookId() == 0L) {
            throw new IllegalArgumentException("bookId为空");
        }
    }
}
