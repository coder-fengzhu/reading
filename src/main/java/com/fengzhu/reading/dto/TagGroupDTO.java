package com.fengzhu.reading.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagGroupDTO {

    private long tagGroupId;

    private String tagGroupName;

}
