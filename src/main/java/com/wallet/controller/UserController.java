package com.wallet.controller;

import com.wallet.common.AuthAccess;
import com.wallet.common.Result;
import com.wallet.entity.User;
import com.wallet.exception.error.ErrorEnum;
import com.wallet.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    @Resource
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @AuthAccess // skip this path interception
    public Result<User> register(@RequestBody User user) {
        if (StringUtils.isBlank(user.getName()) ||
            StringUtils.isBlank(user.getPassword()) ||
            StringUtils.isBlank(user.getUserId())) {
            return Result.Result(ErrorEnum.REQUEST_PARAMETER_ERROR, user);
        }
        if ((user.getUserId().length() < 3 || user.getUserId().length() > 50) ||
            (user.getPassword().length() < 3 || user.getPassword().length() > 20) ||
            user.getName().length() > 30) {
            return Result.Result(ErrorEnum.REQUEST_PARAMETER_ERROR, user);
        }

        userService.register(user);
        return Result.Result(ErrorEnum.SUCCESS, user);
    }

    @PostMapping("/login")
    @AuthAccess // skip this path interception
    public Result<User> login(@RequestBody User user) {
        if (StringUtils.isBlank(user.getUserId()) ||
            StringUtils.isBlank(user.getPassword()) ||
            StringUtils.isBlank(user.getName())) {
            return Result.Result(ErrorEnum.REQUEST_PARAMETER_ERROR, user);
        }
        user = userService.login(user);
        return Result.Result(ErrorEnum.SUCCESS, user);
    }
}
