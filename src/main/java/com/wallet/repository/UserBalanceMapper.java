package com.wallet.repository;

import com.wallet.entity.UserBalance;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;


public interface UserBalanceMapper {
    void insert(UserBalance userBalance);
    int updateBalance(@Param("userId") String userId, @Param("amount") BigDecimal amount);
    UserBalance selectByUserId(String userId);
    int deleteByUserId(String userId);
}
