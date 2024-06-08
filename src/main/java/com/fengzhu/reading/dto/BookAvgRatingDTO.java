package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookAvgRatingDTO {

    private long id;

    private long bookId;

    private float avgRatingScore;
}
