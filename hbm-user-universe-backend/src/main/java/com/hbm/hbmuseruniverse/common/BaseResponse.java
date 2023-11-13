package com.hbm.hbmuseruniverse.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lenovo
 */
@Data
public class BaseResponse<T> implements Serializable {
    /**
     * 编码
     */
    private int code;

    /**
     * 数据
     */
    private T Data;

    /**
     * 消息
     */
    private String message;

    /**
     * 详细描述
     */
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        Data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, String message, String description) {
        this(code, null, message, description);
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }



    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
