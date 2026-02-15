package com.app.repo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.entity.TransactionType;

public interface TransactionView {

    Integer getId();
    TransactionType getType();
    BigDecimal getAmount();
    BigDecimal getBalanceBefore();
    BigDecimal getBalanceAfter();
    Integer getPerformedBy();
    Integer getApprovedBy();
    String getApprovalStatus();
    LocalDateTime getDoneAt();
}

