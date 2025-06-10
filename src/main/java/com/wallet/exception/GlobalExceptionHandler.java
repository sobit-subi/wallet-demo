package com.wallet.exception;

import com.wallet.common.Result;
import com.wallet.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        log.warn("paramaters validation error: {}", errors);

        return Utils.CODE_VALIDATION_ERROR + errors;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleServiceException(ServiceException e) {
        log.warn(e.getCode(), e.getMessage());
        return Result.Result(e.getCode(), e.getMessage(), null);
    }

    @ExceptionHandler(WalletException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWalletException(WalletException ex) {
        log.error("wallet operation error: {}", ex.getMessage());
        return Utils.CODE_WALLET_ERROR + ex.getMessage();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInsufficientBalance(InsufficientBalanceException ex) {
        log.warn("insufficient balance : {}", ex.getMessage());
        return Utils.CODE_INSUFFICIENT_BALANCE + ex.getMessage();
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleWalletNotFound(WalletNotFoundException ex) {
        log.warn("wallet does not exist: {}", ex.getMessage());
        return Utils.CODE_WALLET_NOT_FOUND + ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllExceptions(Exception ex) {
        log.error("system error: {}", ex.getMessage(), ex);
        return Utils.CODE_INTERNAL_ERROR + ex.getMessage();
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTransactionNotFound(TransactionNotFoundException ex) {
        log.warn("transaction record does not exist: {}", ex.getMessage());
        return Utils.CODE_TRANSACTION_NOT_FOUND + ex.getMessage();
    }
}
