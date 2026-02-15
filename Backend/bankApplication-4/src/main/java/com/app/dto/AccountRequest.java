package com.app.dto;



import java.math.BigDecimal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequest {

    @NotBlank
    private String userName;

    @Email
    private String emailId;
    
    @Pattern(regexp = "^[0-9]{10}$")
    private String phoneNum;

    @NotNull
    private BigDecimal balance;
}
