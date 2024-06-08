package com.fengzhu.reading.dto;

import com.fengzhu.reading.dataObject.BaseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRatingDTO extends BaseEntity {

    private long id;

    private long bookId;

    private long userId;

    private int score;

    private long rateDate;


}
