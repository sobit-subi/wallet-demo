package com.wallet.service;

import com.wallet.entity.PageResponse;
import com.wallet.entity.TransactionRecord;

import java.math.BigDecimal;

public interface WalletService {
    public void createUserWallet(String userId);
    public void executeTransaction(String userId, BigDecimal amount,
                                   String type, String description);
    public BigDecimal getBalance(String userId);
    public PageResponse<TransactionRecord> getTransactions(String userId, int page, int size);
    public void deleteUserWallet(String userId);
}
