package com.wallet.service.impl;

import com.wallet.entity.User;
import com.wallet.exception.ServiceException;
import com.wallet.exception.error.ErrorEnum;
import com.wallet.repository.UserMapper;
import com.wallet.service.UserService;
import com.wallet.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User register(User user) {
        User dbUser = userMapper.findUserByUserId(user.getUserId());
        if (dbUser != null) {
            throw new ServiceException(ErrorEnum.REQUEST_FAILED.getCode(), "user already in use!");
        }
        userMapper.createUser(user);
        return user;
    }

    @Override
    public User login(User user) {
        User dbUser = userMapper.findUserByUserId(user.getUserId());
        if (dbUser == null) {
            throw new ServiceException(ErrorEnum.REQUEST_PARAMETER_ERROR.getCode(), "please register first!");
        }
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException(ErrorEnum.REQUEST_PARAMETER_ERROR.getCode(), "username or password incorrect");
        }
        String token = TokenUtils.generateToken(user.getUserId(), user.getPassword());
        dbUser.setToken(token);
        userMapper.updateToken(dbUser);
        return dbUser;
    }
}
