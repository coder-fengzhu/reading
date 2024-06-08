package com.fengzhu.reading.controller;

import com.fengzhu.reading.CommonResponse;
import com.fengzhu.reading.dto.BookAvgRatingDTO;
import com.fengzhu.reading.dto.BookDTO;
import com.fengzhu.reading.dto.BookRankDTO;
import com.fengzhu.reading.dto.BookReviewDTO;
import com.fengzhu.reading.service.BookAvgRatingService;
import com.fengzhu.reading.service.BookReviewService;
import com.fengzhu.reading.service.BookService;
import com.fengzhu.reading.validator.BookValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookAvgRatingService bookAvgRatingService;

    @Autowired
    private BookValidator bookValidator;

    @PostMapping("/book")
    public CommonResponse<BookDTO> addNewBook(@RequestBody BookDTO bookDTO) {
        bookValidator.validateAddNewBook(bookDTO);
        return CommonResponse.newSuccess(bookService.addNewBook(bookDTO));
    }

    @PutMapping("/book")
    public CommonResponse<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        return CommonResponse.newSuccess(bookService.updateBook(bookDTO));
    }

    @GetMapping("/book")
    public CommonResponse<Page<BookDTO>> lookupBooks(@RequestParam(required = false) Long tagId,
                                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                     @RequestParam(required = false, defaultValue = "0") Integer pageNumber) {

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return CommonResponse.newSuccess(bookService.lookupBooks(pageRequest, tagId));
    }

    @GetMapping("/book/{bookId}")
    public CommonResponse<BookDTO> getBookById(@PathVariable Long bookId) {
        CompletableFuture<BookDTO> bookFuture = CompletableFuture.
                supplyAsync(() -> bookService.getBookDTOById(bookId));

        CompletableFuture<Page<BookReviewDTO>> bookReviewFuture = CompletableFuture.
                supplyAsync(() -> bookReviewService.getBookReviewDTOListByBookId(bookId, PageRequest.of(0, 10, Sort.by("likeCount").descending())));

        CompletableFuture<BookAvgRatingDTO> bookAvgRatingFuture = CompletableFuture
                .supplyAsync(() -> bookAvgRatingService.getAvgRatingByBookId(bookId));

        // 利用CompletableFuture实现异步加载book， rating, review
        CompletableFuture<Void> allTask = CompletableFuture.allOf(bookFuture, bookReviewFuture, bookAvgRatingFuture);
        CompletableFuture<BookDTO> combinedFuture = allTask.thenApply(v -> {
            BookDTO bookDTO = bookFuture.join();
            Page<BookReviewDTO> bookReviewDTOList = bookReviewFuture.join();
            BookAvgRatingDTO bookAvgRatingDTO = bookAvgRatingFuture.join();
            bookDTO.setBookReviewDTOList(bookReviewDTOList);
            bookDTO.setBookAvgRatingDTO(bookAvgRatingDTO);
            return bookDTO;
        }).whenComplete((res, ex) -> {
            if (ex != null) {
                log.error("getBookById occur exception", ex);
            }
        });
        try {
            return CommonResponse.newSuccess(combinedFuture.get());
        } catch (Throwable e) {
            return CommonResponse.newFail("获取book信息失败", null);
        }

    }

    @GetMapping("/book/rank")
    public CommonResponse<List<BookRankDTO>> getBookRank(@RequestParam(required = false, defaultValue = "10") int size) {
        return CommonResponse.newSuccess(bookService.getBookRank(size));
    }

    @GetMapping("/book/search")
    public CommonResponse<Page<BookDTO>> searchBooks(@RequestParam String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return CommonResponse.newSuccess(null);
        }
        return CommonResponse.newSuccess(bookService.searchBooks(keyword));
    }


}
