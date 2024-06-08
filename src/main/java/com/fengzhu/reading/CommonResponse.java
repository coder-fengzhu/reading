package com.fengzhu.reading;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private T data;
    private boolean success;
    private String errorMsg;

    public static <K> CommonResponse<K> newSuccess(K data) {
        CommonResponse<K> response = new CommonResponse<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <K> CommonResponse<K> newFail(String errorMsg, K data) {
        CommonResponse<K> response = new CommonResponse<>();
        response.setErrorMsg(errorMsg);
        response.setSuccess(false);
        response.setData(data);
        return response;
    }

}
