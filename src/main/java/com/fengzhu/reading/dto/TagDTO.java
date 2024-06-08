package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {

    private long id;

    private String tagName;

    private long tagGroupId;

    private long tagValue;

}
