package com.wallet.repository;

import com.wallet.entity.TransactionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionRecordMapper {
    void insert(TransactionRecord record);
    List<TransactionRecord> selectByUserIdWithPagination(
            @Param("userId") String userId,
            @Param("offset") int offset,
            @Param("limit") int limit
    );
    int countByUserId(String userId);
    int deleteByUserId(String userId);

}
