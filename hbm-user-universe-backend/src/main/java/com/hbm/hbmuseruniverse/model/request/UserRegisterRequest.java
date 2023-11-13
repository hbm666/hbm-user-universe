package com.hbm.hbmuseruniverse.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author lenovo
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 3152055714166512143L;

    /**
     * 账号
     */
    public String userAccount;

    /**
     * 密码
     */
    public String userPassword;

    /**
     * 校验码
     */
    public String checkPassword;
}
