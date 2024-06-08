package com.fengzhu.reading.converter;

import com.fengzhu.reading.dataObject.BookReview;
import com.fengzhu.reading.dto.BookReviewDTO;

public class BookReviewConverter {

    public static BookReview convertToBookReview(BookReviewDTO bookReviewDTO) {
        if (bookReviewDTO == null) {
            return null;
        }
        return BookReview.builder()
                .id(bookReviewDTO.getId())
                .bookId(bookReviewDTO.getBookId())
                .reviewContent(bookReviewDTO.getReviewContent())
                .userId(bookReviewDTO.getUserId())
                .likeCount(bookReviewDTO.getLikeCount())
                .dislikeCount(bookReviewDTO.getDislikeCount())
                .build();
    }

    public static BookReviewDTO convertToBookReviewDTO(BookReview bookReview) {
        if (bookReview == null) {
            return null;
        }

        return BookReviewDTO.builder()
                .id(bookReview.getId())
                .bookId(bookReview.getBookId())
                .reviewContent(bookReview.getReviewContent())
                .likeCount(bookReview.getLikeCount())
                .dislikeCount(bookReview.getDislikeCount())
                .createTime(bookReview.getCreateTime())
                .updateTime(bookReview.getUpdateTime())
                .build();
    }
}
