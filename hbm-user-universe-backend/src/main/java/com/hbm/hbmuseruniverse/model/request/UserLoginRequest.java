package com.hbm.hbmuseruniverse.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author lenovo
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -1471284536336702460L;

    /**
     * 账号
     */
    public String userAccount;

    /**
     * 密码
     */
    public String userPassword;
}
