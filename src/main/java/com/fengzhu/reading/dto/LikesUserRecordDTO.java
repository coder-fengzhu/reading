package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikesUserRecordDTO {

    private long id;

    private long userId;

    private long businessId;

    private long itemId;

    private boolean likes;


}
