package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.dto.AccountContactUpdateRequest;
import com.app.dto.AccountDetailsResponse;
import com.app.dto.AccountRequest;
import com.app.dto.AccountResponse;
import com.app.entity.Account;
import com.app.entity.User;
import com.app.exception.BankAccountNotFoundException;
import com.app.mapper.AccountMapper;
//import com.app.repo.Account;
import com.app.repo.AccountRepository;
import com.app.repo.AccountSummary;
import com.app.repo.UserRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class AccountServiceImpl implements AccountService{
	private AccountRepository accountRepo;
	private UserRepository userRepo;

	private AccountMapper accountMapper;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepo,
	                          UserRepository userRepo,
	                          AccountMapper accountMapper) {
	    this.accountRepo = accountRepo;
	    this.userRepo = userRepo;
	    this.accountMapper = accountMapper;
	}

	@Override
	public List<AccountResponse> getAll() {
	    return accountRepo.findAll()
	            .stream()
	            .map(accountMapper::toResponse)
	            .toList();
	}


//	@Override
//public AccountResponse getById(int id) {
//		 Account account = accountRepo.findById(id).orElseThrow(()-> new BankAccountNotFoundException("Accoutn with given id doesn't exist"));
//		return accountMapper.toResponse(account);
//	}

	@Override
	public AccountResponse addAccount(AccountRequest request) {

	    Account account = accountMapper.toEntity(request);

	    account.setAccountNum(generateAccountNumber());
	    account.setCreatedById(getLoggedInUserId());

	    Account saved = accountRepo.save(account);
	    return accountMapper.toResponse(saved);
	}


	// make it soft delete later 
	@Override
	public void deleteAccount(int id) {
		accountRepo.deleteById(id);
	}
	
	
	@Override
	public AccountResponse updateAccountContact(int id,AccountContactUpdateRequest request) {

	    Account acc = accountRepo.findById(id)
	            .orElseThrow(() ->
	                new BankAccountNotFoundException("Account not found"));

	    acc.setEmailId(request.getEmailId());
	    acc.setPhoneNum(request.getPhoneNum());

	    return accountMapper.toResponse(acc);
	}

	@Override
	public AccountDetailsResponse getAccountDetails(int id) {

	    Account account = accountRepo.findAccountWithTransactions(id)
	            .orElseThrow(() ->
	                    new BankAccountNotFoundException("Account not found"));

	    return accountMapper.toDetailsResponse(account);
	}

//	@Override
//	public List<AccountSummary> getAccountSummaries() {
//		return accountRepo.findAccountSummaries();
//	}
	
//	
//	@Override
//	public Account getAccountWithTransactions(int id) {
//		return accountRepo.findAccountWithTransactions(id)
//				.orElseThrow(() -> new RuntimeException("Account not found: " + id));
//	}
	
	private String generateAccountNumber() {

	    String accountNumber;
	    
	    do {
	        long number = (long) (Math.random() * 1_000_000_000_000L);
	        accountNumber = String.format("%012d", number);
	    } 
	    while (accountRepo.findByAccountNum(accountNumber).isPresent());

	    return accountNumber;
	}

	private int getLoggedInUserId() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    User user = userRepo.findByUserName(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return user.getId();
	}


	
	
}
