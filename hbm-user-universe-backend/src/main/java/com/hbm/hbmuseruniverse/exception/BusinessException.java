package com.hbm.hbmuseruniverse.exception;

import com.hbm.hbmuseruniverse.common.ErrorCode;

/**
 * @author lenovo
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getMessage(), errorCode.getCode(), errorCode.getDescription());
    }

    public BusinessException(ErrorCode errorCode, String description) {
        this(errorCode.getMessage(), errorCode.getCode(), description);
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
