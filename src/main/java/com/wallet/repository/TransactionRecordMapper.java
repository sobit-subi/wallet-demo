package com.wallet.repository;

import com.wallet.entity.PageResponse;
import com.wallet.entity.TransactionRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionRecordMapper {
    void insert(TransactionRecord record);
    List<TransactionRecord> selectByUserId(@Param("userId") String userId);
    int countByUserId(String userId);
    int deleteByUserId(String userId);

}
