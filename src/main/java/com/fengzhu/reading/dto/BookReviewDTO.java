package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReviewDTO {

    private long id;

    private long bookId;

    private long userId;

    private String reviewContent;

    private long likeCount;

    private long dislikeCount;

    private long createTime;

    private long updateTime;

}
