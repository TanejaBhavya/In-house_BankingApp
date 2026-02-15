package com.app.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.dto.AccountDetailsResponse;
import com.app.dto.AccountRequest;
import com.app.dto.AccountResponse;
import com.app.dto.TransactionResponse;
import com.app.entity.Account;
import com.app.entity.Transaction;


@Component
public class AccountMapper {

    // RequestDTO → Entity
    public Account toEntity(AccountRequest dto) {
        return Account.builder()
                .userName(dto.getUserName())
                .emailId(dto.getEmailId())
                .phoneNum(dto.getPhoneNum())
                .balance(dto.getBalance())
                .build();
    }

    // Entity → ResponseDTO
    public AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNum(account.getAccountNum())
                .userName(account.getUserName())
                .emailId(account.getEmailId())
                .phoneNum(account.getPhoneNum())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build();
    }

    // Update entity from DTO
    public void updateEntity(AccountRequest dto, Account account) {
        account.setEmailId(dto.getEmailId());
        account.setPhoneNum(dto.getPhoneNum());
//        account.setUserName(dto.getUserName());
//        account.setBalance(dto.getBalance());
    }
    
    
    
    public AccountDetailsResponse toDetailsResponse(Account account) {

    	List<TransactionResponse> txList = account.getTransactions()
    	        .stream()
    	        .map((Transaction tx) -> TransactionResponse.builder()
    	                .id(tx.getId())
    	                .type(tx.getType().name())
    	                .amount(tx.getAmount())
    	                .balanceBefore(tx.getBalanceBefore())
    	                .balanceAfter(tx.getBalanceAfter())
    	                .performedBy(tx.getPerformedBy())
    	                .approvedBy(tx.getApprovedBy())
    	                .approvalStatus(tx.getApprovalStatus().name())
    	                .transactionTime(tx.getDoneAt())
    	                .build())
    	        .toList();


        return AccountDetailsResponse.builder()
                .id(account.getId())
                .accountNum(account.getAccountNum())
                .userName(account.getUserName())
                .emailId(account.getEmailId())
                .phoneNum(account.getPhoneNum())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .transactions(txList)
                .build();
    }


    
}
