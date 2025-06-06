package com.wallet.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRecord {
    private Long id;
    private String userId;
    private BigDecimal amount;
    private String type; // RECHARGE, CONSUME, REFUND
    private String description;
    private Date createTime;
}
