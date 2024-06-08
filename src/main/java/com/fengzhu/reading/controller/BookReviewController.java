package com.fengzhu.reading.controller;

import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.BookReviewDTO;
import com.fengzhu.reading.service.BookReviewService;
import com.fengzhu.reading.validator.BookReviewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookReviewController {

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookReviewValidator bookReviewValidator;

    @PostMapping("/book/review")
    public CommonResponse<Long> addNewBookReview(@RequestBody BookReviewDTO bookReviewDTO) {
        bookReviewValidator.validateAddNewBookReview(bookReviewDTO);
        return CommonResponse.newSuccess(bookReviewService.addNewBookReview(bookReviewDTO));
    }

    @GetMapping("/book/review/{bookId}")
    public CommonResponse<Page<BookReviewDTO>> getBookReviewByBookId(@PathVariable long bookId, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
                                                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                     @RequestParam(required = false, defaultValue = "likeCount") String sortField) {

        return CommonResponse.newSuccess(bookReviewService.getBookReviewDTOListByBookId(bookId,
                PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending())));
    }

}
