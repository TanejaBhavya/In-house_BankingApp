package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class TransactionResponse {

    private int id;
    private String type;
    private BigDecimal amount;

    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;

    private int performedBy;
    private int approvedBy;

    private String approvalStatus;

    private LocalDateTime transactionTime;
}

//public class TransactionResponse {
//
//    private int id;
//    private String type;
//    private BigDecimal amount;
//    private LocalDateTime transactionTime;
//    private String approvalStatus;
//}
