package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.BookAvgRatingConverter;
import com.fengzhu.reading.repository.BookAvgRatingRepository;
import com.fengzhu.reading.dataObject.BookAvgRating;
import com.fengzhu.reading.dto.BookAvgRatingDTO;
import com.fengzhu.reading.enums.Enable;
import com.fengzhu.reading.service.BookAvgRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookAvgRatingServiceImpl implements BookAvgRatingService {

    @Autowired
    private BookAvgRatingRepository bookAvgRatingRepository;

    @Override
    public BookAvgRatingDTO getAvgRatingByBookId(long bookId) {
        BookAvgRating bookAvgRating = bookAvgRatingRepository.findByBookIdAndEnable(bookId, Enable.TRUE.isEnable()).
                orElseThrow(() -> new IllegalArgumentException("bookId不存在评分"));

        return BookAvgRatingConverter.convertToBookAvgRatingDTO(bookAvgRating);
    }
}
