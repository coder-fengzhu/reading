package com.fengzhu.reading.controller;

import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.BookRatingDTO;
import com.fengzhu.reading.service.BookRatingService;
import com.fengzhu.reading.validator.BookRatingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookRatingController {

    @Autowired
    private BookRatingValidator bookRatingValidator;

    @Autowired
    private BookRatingService bookRatingService;

    @PostMapping("/book/rating")
    public CommonResponse<Long> addNewBookRating(@RequestBody BookRatingDTO bookRatingDTO) {
        bookRatingValidator.validateAddNewBookRating(bookRatingDTO);
        return CommonResponse.newSuccess(bookRatingService.addNewBookRating(bookRatingDTO));
    }

}
