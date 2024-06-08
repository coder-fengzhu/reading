package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRankDTO {

    private long bookId;

    private String bookName;

    private long hit;

    private int order;


}
