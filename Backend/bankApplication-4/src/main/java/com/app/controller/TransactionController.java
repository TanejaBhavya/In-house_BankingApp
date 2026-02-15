package com.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DepositRequest;
import com.app.dto.TransferRequest;
import com.app.dto.WithdrawRequest;
import com.app.entity.Transaction;
import com.app.repo.TransactionView;
import com.app.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "v1/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
	
	private TransactionService transactionService;
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	// transfer
	@PreAuthorize("hasRole('CLERK') or hasRole('MANAGER')")
    @PutMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransferRequest request) {
        transactionService.transfer(request.fromAccountId(), request.toAccountId(), request.amount());
        return ResponseEntity.noContent().build();
    }

	@PreAuthorize("hasRole('CLERK') or hasRole('MANAGER')")
    @PutMapping("/deposit")
    public ResponseEntity<Void> deposit(@Valid @RequestBody DepositRequest request) {
        transactionService.deposit(request.accountId(), request.amount());
        return ResponseEntity.noContent().build();
    }
	@PreAuthorize("hasRole('CLERK') or hasRole('MANAGER')")
	@PutMapping("/withdraw")
	public ResponseEntity<String> withdraw(@Valid @RequestBody WithdrawRequest request) {

	    String status = transactionService.withdraw(
	            request.accountId(),
	            request.amount()
	    );

	    return ResponseEntity.ok(status);
	}
	
	@PreAuthorize("hasRole('CLERK') or hasRole('MANAGER')")
	@GetMapping("account/{id}")
	public ResponseEntity<List<TransactionView>> getTransactionView(@PathVariable int id) {
	    return ResponseEntity.ok(transactionService.getTransactionView(id));
	}

	
	@PreAuthorize("hasRole('CLERK') or hasRole('MANAGER')")
    @GetMapping("/account/{id}/count")
    public ResponseEntity<Long> getTransactionCount(@PathVariable int id) {
        return ResponseEntity.ok(transactionService.getTransactionCount(id));
    }
	
	
	
	
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/approve/{txId}")
    public ResponseEntity<Void> approve(@PathVariable int txId) {
        transactionService.approveWithdrawal(txId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/pending")
    public ResponseEntity<List<Transaction>> getPending() {
        return ResponseEntity.ok(transactionService.getPendingApprovals());
    }

	
}
