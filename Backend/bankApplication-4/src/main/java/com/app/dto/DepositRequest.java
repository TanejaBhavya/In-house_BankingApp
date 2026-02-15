package com.app.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositRequest(
		 @NotNull(message = "From account id is required")
		Integer accountId,
		
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
		BigDecimal amount) {}
