package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.BookRating;
import com.fengzhu.reading.dto.BookRatingDTO;

public class BookRatingConverter {

    public static BookRating converToBookRating(BookRatingDTO bookRatingDTO) {
        if (bookRatingDTO == null) {
            return null;
        }

        return BookRating.builder()
                .id(bookRatingDTO.getId())
                .bookId(bookRatingDTO.getBookId())
                .userId(bookRatingDTO.getUserId())
                .score(bookRatingDTO.getScore())
                .rateDate(bookRatingDTO.getRateDate()).build();
    }

    public static BookRatingDTO convertToBookRatingDTO(BookRating bookRating) {
        if (bookRating == null) {
            return null;
        }

        return BookRatingDTO.builder()
                .id(bookRating.getId())
                .bookId(bookRating.getBookId())
                .userId(bookRating.getUserId())
                .score(bookRating.getScore())
                .rateDate(bookRating.getRateDate()).build();
    }
}
