package com.hbm.hbmuseruniverse.service;

import com.hbm.hbmuseruniverse.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author lenovo
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("一张白纸");
        user.setUserAccount("wokao");
        user.setUserPassword("123456");
        user.setAvatarUrl("https://tupian.qqw21.com/article/UploadPic/2020-8/20208522181570993.jpg");
        user.setGender(0);
        user.setPhone("1234567890");
        user.setEmail("1208888888@qq.com");
        boolean save = userService.save(user);
        System.out.println(user.getId());

        Assertions.assertTrue(save);
    }

    @Test
    public void testUserRegister() {
        String userAccount = "123456";
        String userPassword = "";
        String checkPassword = "abcde";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "123";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "123456";
        userPassword = "12346";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userPassword = "12345678";
        checkPassword = "12345670";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "123456.";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "lucy";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "woku";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertNotEquals(-1, result);
    }
}