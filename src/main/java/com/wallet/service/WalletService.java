package com.wallet.service;

import com.wallet.entity.TransactionRecord;
import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    public void createUserWallet(String userId, String token);
    public void executeTransaction(String userId, BigDecimal amount,
                                   String type, String description, String token);
    public BigDecimal getBalance(String userId, String token);
    public List<TransactionRecord> getTransactions(String userId, String token);
    public void deleteUserWallet(String userId, String token);
}
