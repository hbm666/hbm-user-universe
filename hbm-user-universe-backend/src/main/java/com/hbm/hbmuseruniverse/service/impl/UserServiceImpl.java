package com.hbm.hbmuseruniverse.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbm.hbmuseruniverse.model.entity.User;
import com.hbm.hbmuseruniverse.service.UserService;
import com.hbm.hbmuseruniverse.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.TestOnly;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lenovo
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-11-10 16:16:48
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

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
        String salt = "lucy";
        String password = DigestUtil.md5Hex(DigestUtil.md5Hex(userPassword + salt) + salt);

        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        // 插入数据
        userMapper.insert(user);

        return user.getId();
    }
}




