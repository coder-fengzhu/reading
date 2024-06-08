package com.fengzhu.reading.service;

import com.fengzhu.reading.dto.BookAvgRatingDTO;

public interface BookAvgRatingService {

    BookAvgRatingDTO getAvgRatingByBookId(long bookId);

}
