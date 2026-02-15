package com.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.app.dto.AccountContactUpdateRequest;
import com.app.dto.AccountDetailsResponse;
import com.app.dto.AccountRequest;
import com.app.dto.AccountResponse;
import com.app.entity.Account;
import com.app.repo.AccountSummary;

public interface AccountService {
    public List<AccountResponse> getAll();
//    public AccountResponse getById(int id);
	
    public AccountResponse addAccount(AccountRequest  account);
    public void deleteAccount(int id);
//    public Account updateAccountContact(int id, String newEmail, String newPhone);
    public AccountResponse updateAccountContact(int id, AccountContactUpdateRequest request);
//	AccountSummary getAccountSummaryByAccountNumber(int id);
//	List<AccountSummary> getAccountSummaries();
//	Account getAccountWithTransactions(int id);
	AccountDetailsResponse getAccountDetails(int id);

//    public void transfer(int fromAccId, int toAccId, BigDecimal amount);
//    public void deposit(int accId, BigDecimal amount);
//    public void withdraw(int accId, BigDecimal amount);
}
