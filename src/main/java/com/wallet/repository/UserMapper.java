package com.wallet.repository;

import com.wallet.entity.User;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    void createUser(User user);
    User findUserByUserId(@Param("userId")String userId);
    void updateToken(User user);
}
