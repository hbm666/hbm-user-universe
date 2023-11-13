package com.hbm.hbmuseruniverse.controller;

import com.hbm.hbmuseruniverse.model.entity.User;
import com.hbm.hbmuseruniverse.model.request.UserLoginRequest;
import com.hbm.hbmuseruniverse.model.request.UserRegisterRequest;
import com.hbm.hbmuseruniverse.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.hbm.hbmuseruniverse.constant.UserConstant.ADMIN_ROLE;
import static com.hbm.hbmuseruniverse.constant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author lenovo
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUser(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return null;
        }

        if (StringUtils.isBlank(username)) {
            return null;
        }

        return userService.searchUser(username);
    }

    @PostMapping("/delete")
    public boolean deleteUser(long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }

        if (id < 0) {
            return false;
        }

        return userService.removeById(id);
    }

    @GetMapping("current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj == null) {
            return null;
        }

        User currentUser = (User) userObj;
        Long userId = currentUser.getId();
        // todo 校验用户是否合法，这里缺失了一些状态，如用户是否被封号
        User user = userService.getById(userId);
        return user;
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj == null) {
            return false;
        }

        User user = (User) userObj;
        return user != null && user.getUserRole().equals(ADMIN_ROLE);
    }
}
