package com.hbm.hbmuseruniverse.service;

import com.hbm.hbmuseruniverse.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lenovo
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-10 16:16:48
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount    账号
     * @param userPassword   密码
     * @param checkPassword  校验码
     * @param invitationCode 邀请码
     * @return 注册用户的id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String invitationCode);

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request      request
     * @return             脱敏后的用户数据
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 查找用户
     *
     * @param username 用户名
     * @return         用户列表
     */
    List<User> searchUser(String username);

    /**
     * 用户注销
     * @param request request
     * @return        1 表示注销成功
     */
    int userLogout(HttpServletRequest request);
}
