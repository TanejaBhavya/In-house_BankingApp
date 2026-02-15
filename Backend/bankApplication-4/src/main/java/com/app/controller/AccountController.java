package com.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AccountContactUpdateRequest;
import com.app.dto.AccountDetailsResponse;
import com.app.dto.AccountRequest;
import com.app.dto.AccountResponse;
import com.app.entity.Account;
import com.app.repo.AccountSummary;
import com.app.service.AccountService;

@RestController
@RequestMapping(path = "v1/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
		private AccountService accountService;
		
		public AccountController(AccountService accountService) {
			this.accountService = accountService;
		}
		@PreAuthorize("hasAnyRole('MANAGER','CLERK')")
		@GetMapping
		public List<AccountResponse> getAll(){
			return accountService.getAll();
		}
		
//		@PreAuthorize("hasAnyRole('MANAGER','CLERK')")
//		@GetMapping(path = "{id}")
//		public Account getById(@PathVariable(name = "id") int id) {
//			return accountService.getById(id);
//		}
		
		
		@PreAuthorize("hasRole('MANAGER')")
		@DeleteMapping(path = "{id}")
		public ResponseEntity<Void> deleteById(@PathVariable(name = "id") int id) {
			 accountService.deleteAccount(id);
			 return ResponseEntity.noContent().build();
		}
		
		
		@PreAuthorize("hasAnyRole('MANAGER','CLERK')")
		@PutMapping("{id}/contact")
		public ResponseEntity<AccountResponse> updateContact(
		        @PathVariable int id,
		        @RequestBody AccountContactUpdateRequest request) {

		    return ResponseEntity.ok(
		            accountService.updateAccountContact(id, request)
		    );
		}

		
//		@PreAuthorize("hasRole('MANAGER','CLERK')")
//		@PutMapping(path = "{id}")
//		public ResponseEntity<Account> updateAccount(@PathVariable(name = "id") int id,  @RequestBody Account account) {
//		    Account updated = accountService.updateAccountContact(id, account);
//		    return ResponseEntity
//		            .status(HttpStatus.OK)
//		            .body(updated);
//		}
		
		@PreAuthorize("hasRole('MANAGER')")
		@PostMapping
		public ResponseEntity<AccountResponse> addAccount(@RequestBody AccountRequest account) {
			AccountResponse  saved = accountService.addAccount(account);
		    return ResponseEntity
		            .status(HttpStatus.CREATED)
		            .body(saved);
		}
		
		
		// version 2
		 @PreAuthorize("hasAnyRole('MANAGER','CLERK')")
	    @GetMapping("summary/{id}")
	    public AccountDetailsResponse getAccountSummaries(@PathVariable(name="id") int id) {
	        return accountService.getAccountDetails(id);
	    }
//		 @PreAuthorize("hasAnyRole('MANAGER','CLERK')")
//	    @GetMapping("summary/by-account-number/{id}")
//	    public AccountSummary getAccountSummaryByAccountNumber(
//	            @PathVariable int id) {
//	        return accountService.getAccountSummaryByAccountNumber(id);
//	    }
//		 @PreAuthorize("hasAnyRole('MANAGER','CLERK')")
//	    @GetMapping("{id}/with-transactions")
//	    public Account getAccountWithTransactions(@PathVariable int id) {
//	        return accountService.getAccountWithTransactions(id);
//	    }
		
}
