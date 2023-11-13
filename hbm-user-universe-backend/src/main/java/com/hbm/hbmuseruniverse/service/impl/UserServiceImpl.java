package com.hbm.hbmuseruniverse.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbm.hbmuseruniverse.mapper.UserMapper;
import com.hbm.hbmuseruniverse.model.entity.User;
import com.hbm.hbmuseruniverse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.hbm.hbmuseruniverse.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author lenovo
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-11-10 16:16:48
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
    private static final String SALT = "lucy";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

        // 用户账号长度不小于4位
        if (userAccount.length() < 4) {
            return -1;
        }

        // 用户密码长度不小于8位
        if (userPassword.length() < 8) {
            return -1;
        }

        // 密码和校验码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账号不能包含特殊字符
        Pattern pattern= Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(userAccount);
        // 匹配上的时候返回true,匹配不通过返回false
        if (!matcher.matches()) {
            return -1;
        }

        // 账号不能重复
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_account", userAccount);
        Long count = userMapper.selectCount(wrapper);
        if (count >= 1) {
            return -1;
        }

        // 对密码加密
        String encryptPassword = DigestUtil.md5Hex(DigestUtil.md5Hex(userPassword + SALT) + SALT);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        // 插入数据
        userMapper.insert(user);

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        // 用户账号长度不小于4位
        if (userAccount.length() < 4) {
            return null;
        }

        // 用户密码长度不小于8位
        if (userPassword.length() < 8) {
            return null;
        }

        // 账号不能包含特殊字符
        Pattern pattern= Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(userAccount);
        // 匹配上的时候返回true,匹配不通过返回false
        if (!matcher.matches()) {
            return null;
        }

        // 账号密码是否正确
        String encryptPassword = DigestUtil.md5Hex(DigestUtil.md5Hex(userPassword + SALT) + SALT);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        // 用户信息脱敏
        User safetyUser = getSafetyUser(user);

        // 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);

        return safetyUser;
    }

    @Override
    public List<User> searchUser(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);

        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream()
                .map(this::getSafetyUser)
                .collect(Collectors.toList());
    }

    /**
     * 用户数据脱敏
     *
     * @param user 原用户数据
     * @return     脱敏用户数据
     */
    private User getSafetyUser(User user) {
        if (user == null) {
            return null;
        }

        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }
}




