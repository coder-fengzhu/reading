package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.BookAvgRating;
import com.fengzhu.reading.dto.BookAvgRatingDTO;

public class BookAvgRatingConverter {

    public static BookAvgRatingDTO convertToBookAvgRatingDTO(BookAvgRating bookAvgRating) {
        if (bookAvgRating == null) {
            return null;
        }

        return BookAvgRatingDTO.builder()
                .id(bookAvgRating.getId())
                .bookId(bookAvgRating.getBookId())
                .avgRatingScore(bookAvgRating.getAvgRatingScore()).build();
    }

}
