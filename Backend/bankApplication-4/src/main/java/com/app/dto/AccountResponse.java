package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    private int id;
    private String accountNum;
    private String userName;
    private String emailId;
    private String phoneNum;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
