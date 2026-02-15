package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDetailsResponse {

    private int id;
    private String accountNum;
    private String userName;
    private String emailId;
    private String phoneNum;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    private List<TransactionResponse> transactions;
}
