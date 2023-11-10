package com.hbm.hbmuseruniverse.service;

import com.hbm.hbmuseruniverse.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lenovo
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-10 16:16:48
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
