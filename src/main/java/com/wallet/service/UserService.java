package com.wallet.service;

import com.wallet.entity.User;

public interface UserService {
    public User register(User user);
    public User login(User user);
}
