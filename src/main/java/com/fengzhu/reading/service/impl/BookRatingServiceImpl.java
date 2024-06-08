package com.fengzhu.reading.service.impl;

import com.fengzhu.reading.converter.BookAvgRatingConverter;
import com.fengzhu.reading.converter.BookRatingConverter;
import com.fengzhu.reading.repository.BookAvgRatingRepository;
import com.fengzhu.reading.repository.BookRatingRepository;
import com.fengzhu.reading.dataObject.BookAvgRating;
import com.fengzhu.reading.dataObject.BookRating;
import com.fengzhu.reading.dto.BookAvgRatingDTO;
import com.fengzhu.reading.dto.BookRatingDTO;
import com.fengzhu.reading.enums.Enable;
import com.fengzhu.reading.service.BookRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookRatingServiceImpl implements BookRatingService {

    @Autowired
    private BookRatingRepository bookRatingRepository;

    @Autowired
    private BookAvgRatingRepository bookAvgRatingRepository;

    @Override
    @Transactional
    public long addNewBookRating(BookRatingDTO bookRatingDTO) {
        BookRating bookRating = BookRatingConverter.converToBookRating(bookRatingDTO);
        bookRating = bookRatingRepository.save(bookRating);
        return bookRating.getId();
    }

    @Override
    public BookAvgRatingDTO getBookAvgRatingByBookId(long bookId) {
        BookAvgRating bookAvgRating = bookAvgRatingRepository.findByBookIdAndEnable(bookId, Enable.TRUE.isEnable()).orElse(null);
        return BookAvgRatingConverter.convertToBookAvgRatingDTO(bookAvgRating);
    }
}
