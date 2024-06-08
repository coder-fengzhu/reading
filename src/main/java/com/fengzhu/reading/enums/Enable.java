package com.fengzhu.reading.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Enable {

    TRUE(true, 1), FALSE(false, 0);

    private boolean enable;
    private int dbValue;


}
