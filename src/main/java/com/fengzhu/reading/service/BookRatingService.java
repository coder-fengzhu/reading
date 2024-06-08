package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.BookAvgRatingDTO;
import com.fengzhu.reading.dto.BookRatingDTO;

public interface BookRatingService {

    long addNewBookRating(BookRatingDTO bookRatingDTO);

    BookAvgRatingDTO getBookAvgRatingByBookId(long bookId);
}
