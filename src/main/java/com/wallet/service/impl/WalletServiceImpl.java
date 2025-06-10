package com.wallet.service.impl;

import com.wallet.entity.PageResponse;
import com.wallet.entity.TransactionRecord;
import com.wallet.entity.UserBalance;
import com.wallet.exception.WalletException;
import com.wallet.exception.WalletNotFoundException;
import com.wallet.repository.TransactionRecordMapper;
import com.wallet.repository.UserBalanceMapper;
import com.wallet.service.WalletService;
import com.wallet.util.TokenUtils;
import com.wallet.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private final UserBalanceMapper userBalanceMapper;
    @Autowired
    private final TransactionRecordMapper transactionRecordMapper;
//    @Autowired
//    private final TokenUtils tokenUtils;

    public WalletServiceImpl(UserBalanceMapper userBalanceMapper,
                         TransactionRecordMapper transactionRecordMapper,
                             TokenUtils tokenUtils) {
        this.userBalanceMapper = userBalanceMapper;
        this.transactionRecordMapper = transactionRecordMapper;
//        this.tokenUtils = tokenUtils;
    }
    @Override
    public void createUserWallet(String userId, String token) {
        if (userBalanceMapper.selectByUserId(userId) != null) {
            log.warn(Utils.LOG_WALLET_EXISTS, userId);
            throw new WalletException(Utils.ERR_WALLET_EXISTS + userId);
        }

        if (!TokenUtils.validateToken(userId, token)) {
            throw new WalletException(Utils.ERR_TOKEN_NOT_FOUND + userId);
        }

        // wallet creating
        UserBalance balance = new UserBalance();
        balance.setUserId(userId);
        balance.setBalance(BigDecimal.ZERO);
        userBalanceMapper.insert(balance);
        log.info(Utils.LOG_WALLET_CREATE, userId);
    }

    @Override
    public void executeTransaction(String userId, BigDecimal amount, String type, String description, String token) {
        UserBalance userBalance = userBalanceMapper.selectByUserId(userId);
        if(userBalance == null) {
            log.error(Utils.LOG_WALLET_NOT_FOUND, userId);
            throw new WalletException(Utils.ERR_WALLET_NOT_FOUND);
        }

        if (!TokenUtils.validateToken(userId, token)) {
            throw new WalletException(Utils.ERR_TOKEN_NOT_FOUND + userId);
        }

        BigDecimal balance = userBalance.getBalance().add(amount);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            log.error(Utils.LOG_INSUFFICIENT_BALANCE, userBalance.getBalance());
            throw new WalletException(Utils.ERR_INSUFFICIENT_BALANCE);
        }

        // update balance
        userBalanceMapper.updateBalance(userId, amount);

        // create transaction history
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setUserId(userId);
        transactionRecord.setAmount(amount);
        transactionRecord.setType(type);
        transactionRecord.setDescription(description);
        transactionRecordMapper.insert(transactionRecord);


        // transaction completed log
        log.info(Utils.LOG_TRANSACTION, userId, type, amount);
    }

    @Override
    public BigDecimal getBalance(String userId, String token) {
        UserBalance userBalance = userBalanceMapper.selectByUserId(userId);
        if(userBalance == null) {
            log.error(Utils.LOG_WALLET_NOT_FOUND, userId);
            throw new WalletException(Utils.ERR_WALLET_NOT_FOUND + userId);
        }

        if (!TokenUtils.validateToken(userId, token)) {
            throw new WalletException(Utils.ERR_TOKEN_NOT_FOUND + userId);
        }

        return userBalance.getBalance();
    }

    @Override
    public PageResponse<TransactionRecord> getTransactions(String userId, int page, int size, String token) {

        if (!TokenUtils.validateToken(userId, token)) {
            throw new WalletException(Utils.ERR_TOKEN_NOT_FOUND + userId);
        }
        int total = transactionRecordMapper.countByUserId(userId);
        int offset = (page - 1) * size;

        List<TransactionRecord> records = transactionRecordMapper.selectByUserIdWithPagination(userId, offset, size);

        return new PageResponse<>(records, total, offset, size);
    }

    @Override
    public void deleteUserWallet(String userId, String token) {
        if (!TokenUtils.validateToken(userId, token)) {
            throw new WalletException(Utils.ERR_TOKEN_NOT_FOUND + userId);
        }

        try {
            int recordsDeleted = transactionRecordMapper.deleteByUserId(userId);
            int walletDeleted = userBalanceMapper.deleteByUserId(userId);
            if (walletDeleted == 0) {
                throw new WalletException(Utils.ERR_WALLET_NOT_FOUND + userId);
            }
            log.info(Utils.LOG_WALLET_DELETED, userId, recordsDeleted);
        } catch (WalletNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(Utils.LOG_WALLET_DELETE_FAILED, userId, e);
            throw new WalletException(Utils.ERR_DELETE_FAILED + userId);
        }
    }
}
