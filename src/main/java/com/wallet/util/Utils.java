package com.wallet.util;

public class Utils {
    // Transaction types
    public static final String TRANS_TYPE_RECHARGE = "RECHARGE";
    public static final String TRANS_TYPE_CONSUME = "CONSUME";
    public static final String TRANS_TYPE_REFUND = "REFUND";

    // Transaction description prefix
    public static final String DESC_RECHARGE_PREFIX = "recharge: ";
    public static final String DESC_CONSUME_PREFIX = "consume: ";
    public static final String DESC_REFUND_PREFIX = "refund: ";

    // Error handler
    public static final String ERR_WALLET_EXISTS = "wallet already exists: ";
    public static final String ERR_INSUFFICIENT_BALANCE = "insufficient balance";
    public static final String ERR_WALLET_NOT_FOUND = "wallet does not exist: ";
    public static final String ERR_DELETE_FAILED = "wallet delete failed: ";
    public static final String ERR_TRANSACTION_NOT_FOUND = "Transaction record does not exist: ";
    public static final String ERR_TRANSACTION_DELETE_FAILED = "Transaction record delete failed: ";
    public static final String ERR_TOKEN_NOT_FOUND = "token does not exist: ";
    // Succeed response
    public static final String MSG_WALLET_CREATED = "wallet created: ";
    public static final String MSG_RECHARGE_SUCCESS = "recharge success";
    public static final String MSG_CONSUME_SUCCESS = "consume success";
    public static final String MSG_WALLET_DELETED = "wallet deleted: ";
    public static final String MSG_TRANSACTION_DELETED = "transaction record deleted: ";

    // Validation failed
    public static final String VALID_TRANSACTION_ID_BLANK = "transaction id is blank";
    public static final String VALID_TOKEN_BLANK = "token is blank";
    public static final String VALID_USER_ID_BLANK = "user id is blank";
    public static final String VALID_USER_ID_LENGTH = "user id length in 3-50 letters";
    public static final String VALID_AMOUNT_NULL = "amount is null";
    public static final String VALID_AMOUNT_MIN = "amount is less than 0.1";
    public static final String VALID_DESC_LENGTH = "description length in 250 letters";
    public static final String VALID_PAGE_MIN = "page number is less than 1";
    public static final String VALID_SIZE_MIN = "each page minimum size is 1";
    public static final String VALID_SIZE_MAX = "each page size maximum size is 10";

    // Log data
    public static final String LOG_WALLET_CREATE = "wallet created: {}";
    public static final String LOG_WALLET_EXISTS = "wallet already exists: {}";
    public static final String LOG_WALLET_NOT_FOUND = "wallet not found: {}";
    public static final String LOG_TRANSACTION = "transaction completed - user: {}, type: {}, amount: {}";
    public static final String LOG_INSUFFICIENT_BALANCE = "insufficient balance: {}";
    public static final String LOG_WALLET_DELETED = "wallet deleted - user: {} records deleted: {}";
    public static final String LOG_WALLET_DELETE_FAILED = "wallet delete failed: {}, error: {}";
    public static final String LOG_TRANSACTION_DELETED = "transaction record deleted: {}";
    public static final String LOG_TRANSACTION_DELETE_FAILED = "transaction record delete failed: {} error: {}";
    public static final String LOG_TRANSACTION_RECORD = "transaction record : {}";
    // Error response
    public static final String CODE_VALIDATION_ERROR = "parameters validation error: ";
    public static final String CODE_WALLET_ERROR = "wallet operation error: ";
    public static final String CODE_INSUFFICIENT_BALANCE = "insufficient balance: ";
    public static final String CODE_WALLET_NOT_FOUND = "wallet does not exist: ";
    public static final String CODE_INTERNAL_ERROR = "internal server error: ";
    public static final String CODE_TRANSACTION_NOT_FOUND = "transaction record does not exist: ";
}
