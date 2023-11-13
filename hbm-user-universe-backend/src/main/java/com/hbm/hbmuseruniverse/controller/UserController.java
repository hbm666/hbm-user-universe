package com.hbm.hbmuseruniverse.controller;

import com.hbm.hbmuseruniverse.common.BaseResponse;
import com.hbm.hbmuseruniverse.common.ErrorCode;
import com.hbm.hbmuseruniverse.common.ResultUtils;
import com.hbm.hbmuseruniverse.exception.BusinessException;
import com.hbm.hbmuseruniverse.model.entity.User;
import com.hbm.hbmuseruniverse.model.request.UserLoginRequest;
import com.hbm.hbmuseruniverse.model.request.UserRegisterRequest;
import com.hbm.hbmuseruniverse.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String invitationCode = userRegisterRequest.getInvitationCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, invitationCode)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        long register = userService.userRegister(userAccount, userPassword, checkPassword, invitationCode);
        return ResultUtils.success(register);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        List<User> userList = userService.searchUser(username);
        return ResultUtils.success(userList);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(long id, HttpServletRequest request) {
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        if (id < 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "要删除的人不存在");
        }

        boolean remove = userService.removeById(id);
        return ResultUtils.success(remove);
    }

    @GetMapping("current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        User currentUser = (User) userObj;
        Long userId = currentUser.getId();
        // todo 校验用户是否合法，这里缺失了一些状态，如用户是否被封号
        User user = userService.getById(userId);
        return ResultUtils.success(user);
    }

    @PostMapping("logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        int logout = userService.userLogout(request);
        return ResultUtils.success(logout);
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }

        User user = (User) userObj;
        return user.getUserRole().equals(ADMIN_ROLE);
    }
}
