package com.wallet.exception;

public class TransactionNotFoundException extends WalletException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
