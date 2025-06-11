package com.wallet.controller;

import com.wallet.entity.PageResponse;
import com.wallet.entity.TransactionRecord;
import com.wallet.service.WalletService;
import com.wallet.util.Utils;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@Validated
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createWallet(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            @Size(min = 3, max = 50, message = Utils.VALID_USER_ID_LENGTH)
            String userId
    ) {
        System.out.println("userId: " + userId + " create wallet!");
        walletService.createUserWallet(userId, token);
        return ResponseEntity.ok(Utils.MSG_WALLET_CREATED + userId);
    }

    @PostMapping("/recharge")
    public ResponseEntity<String> recharge(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            String userId,

            @RequestParam
            @NotNull(message = Utils.VALID_AMOUNT_NULL)
            @DecimalMin(value = "0.01", message = Utils.VALID_AMOUNT_MIN)
            BigDecimal amount,

            @RequestParam(required = false)
            @Size(max = 255, message = Utils.VALID_DESC_LENGTH)
            String remark
    ) {
        walletService.executeTransaction(
                userId,
                amount,
                Utils.TRANS_TYPE_RECHARGE,
                Utils.DESC_RECHARGE_PREFIX + (remark != null ? remark : ""),
                token
        );
        return ResponseEntity.ok(Utils.MSG_RECHARGE_SUCCESS);
    }

    @PostMapping("/consume")
    public ResponseEntity<String> consume(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            String userId,

            @RequestParam
            @NotNull(message = Utils.VALID_AMOUNT_NULL)
            @DecimalMin(value = "0.01", message = Utils.VALID_AMOUNT_MIN)
            BigDecimal amount,

            @RequestParam(required = false)
            @Size(max = 255, message = Utils.VALID_DESC_LENGTH)
            String remark
    ) {
        walletService.executeTransaction(
                userId,
                amount.negate(),
                Utils.TRANS_TYPE_CONSUME,
                Utils.DESC_CONSUME_PREFIX + (remark != null ? remark : ""),
                token
        );
        return ResponseEntity.ok(Utils.MSG_CONSUME_SUCCESS);
    }


    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            String userId
    ) {
        return ResponseEntity.ok(walletService.getBalance(userId, token));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionRecord>> getTransactions(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            String userId
    ) {
        return ResponseEntity.ok(walletService.getTransactions(userId, token));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteWallet(
            @RequestHeader("token")
            @NotBlank(message = Utils.VALID_TOKEN_BLANK) String token,

            @RequestParam
            @NotBlank(message = Utils.VALID_USER_ID_BLANK)
            String userId
    ) {
        walletService.deleteUserWallet(userId, token);
        return ResponseEntity.ok(Utils.MSG_WALLET_DELETED + userId);
    }

}
