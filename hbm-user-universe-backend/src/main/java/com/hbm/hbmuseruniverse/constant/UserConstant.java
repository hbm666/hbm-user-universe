package com.hbm.hbmuseruniverse.constant;

/**
 * 用户常量
 *
 * 使用接口默认是public static final类型，就方便很多
 * @author lenovo
 */
public interface UserConstant {
    /**
     * 用户登录态 key 值
     */
    String USER_LOGIN_STATUS = "userLoginStatus";

    // --------- 权限 ----------
    /**
     * 普通用户
     */
    Integer DEFAULT_ROLE = 0;

    /**
     * 普通用户
     */
    Integer ADMIN_ROLE = 1;
}
