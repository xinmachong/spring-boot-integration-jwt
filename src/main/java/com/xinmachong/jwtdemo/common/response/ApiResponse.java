package com.xinmachong.jwtdemo.common.response;

import lombok.Data;

/**
 * @Author meyer@HongYe
 */
@Data
public class ApiResponse {

    private int code;
    private String msg;
    private Object data;
    private int total;

    public ApiResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResponse(int code, String msg, Object data, int total) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }
}
