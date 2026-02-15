package com.app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.entity.Account;
import com.app.entity.ApprovalStatus;
import com.app.entity.Transaction;
import com.app.entity.TransactionType;
import com.app.entity.User;
import com.app.exception.BankAccountNotFoundException;
import com.app.repo.AccountRepository;
import com.app.repo.TransactionRepository;
import com.app.repo.TransactionView;
import com.app.repo.UserRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	
	private AccountRepository accRepo;
	private TransactionRepository transRepo;
	private UserRepository userRepo;
	private static final BigDecimal WITHDRAWAL_LIMIT = new BigDecimal("200000");
	
	
    public TransactionServiceImpl(AccountRepository accRepo,
            TransactionRepository transRepo, UserRepository userRepo) {
this.accRepo = accRepo;
this.transRepo = transRepo;
this.userRepo = userRepo;

}
	
	
	
	@Override
	public void transfer(int fromAccId, int toAccId, BigDecimal amount) {

	    if (fromAccId == toAccId) {
	        throw new RuntimeException("Source and destination accounts must be different");
	    }

	    Account fromAcc = accRepo.findById(fromAccId)
	            .orElseThrow(() ->
	                    new BankAccountNotFoundException("account id " + fromAccId + " not found"));

	    Account toAcc = accRepo.findById(toAccId)
	            .orElseThrow(() ->
	                    new BankAccountNotFoundException("account id " + toAccId + " not found"));

	    if (fromAcc.getBalance().compareTo(amount) < 0) {
	        throw new RuntimeException("Insufficient balance");
	    }

	    // old balances
	    BigDecimal fromOldBal = fromAcc.getBalance();
	    BigDecimal toOldBal = toAcc.getBalance();

	    // debit
	    BigDecimal fromNewBal = fromOldBal.subtract(amount);
	    fromAcc.setBalance(fromNewBal);

	    Transaction txOut = buildTransaction(
	            TransactionType.Transfer_out,
	            amount,
	            fromNewBal,
	            fromAcc,
	            fromOldBal,
	            ApprovalStatus.Completed
	    );

	    // credit
	    BigDecimal toNewBal = toOldBal.add(amount);
	    toAcc.setBalance(toNewBal);

	    Transaction txIn = buildTransaction(
	            TransactionType.Transfer_in,
	            amount,
	            fromNewBal,
	            toAcc,
	            fromOldBal,
	            ApprovalStatus.Completed
	            
	    );
	    
	    transRepo.save(txOut);
	    transRepo.save(txIn);

	    accRepo.save(fromAcc);
	    accRepo.save(toAcc);
		
	}

	@Override
	public void deposit(int accId, BigDecimal amount) {
		Account acc = accRepo.findById(accId).orElseThrow(()-> new BankAccountNotFoundException("account id " + accId + " not found"));
//		acc.setBalance(acc.getBalance().add(amount));
		BigDecimal oldBalance = acc.getBalance();
	    BigDecimal newBalance = acc.getBalance().add(amount);
	    acc.setBalance(newBalance);
	    
	    Transaction tx = buildTransaction(
	            TransactionType.Deposit,
	            amount,
	            newBalance,
	            acc,
	            oldBalance,
	            ApprovalStatus.Completed
	    );
	    transRepo.save(tx);
		accRepo.save(acc);
	}
//
//	@Override
//	public void withdraw(int accId, BigDecimal amount) {
//
//	    Account acc = accRepo.findById(accId)
//	            .orElseThrow(() -> new BankAccountNotFoundException("account id " + accId + " not found"));
//
//	    BigDecimal oldBalance = acc.getBalance();
//
//	    if (amount.compareTo(WITHDRAWAL_LIMIT) <= 0) {
//
//	        if (oldBalance.compareTo(amount) < 0) {
//	            throw new RuntimeException("Insufficient balance");
//	        }
//
//	        BigDecimal newBalance = oldBalance.subtract(amount);
//	        acc.setBalance(newBalance);
//
//	        Transaction tx = buildTransaction(
//	                TransactionType.Withdraw,
//	                amount,
//	                newBalance,
//	                acc,
//	                oldBalance,
//	                ApprovalStatus.Completed
//	        );
//
//	        transRepo.save(tx);
//	        accRepo.save(acc);
//
//	    } else {
//	        Transaction tx = buildTransaction(
//	                TransactionType.Withdraw,
//	                amount,
//	                oldBalance,
//	                acc,
//	                oldBalance,
//	                ApprovalStatus.Pending
//	        );
//
//	        transRepo.save(tx);
//	    }
//	}
	@Override
	public String withdraw(int accId, BigDecimal amount) {

	    Account acc = accRepo.findById(accId)
	            .orElseThrow(() -> new BankAccountNotFoundException("account id " + accId + " not found"));

	    BigDecimal oldBalance = acc.getBalance();

	    if (amount.compareTo(WITHDRAWAL_LIMIT) <= 0) {

	        if (oldBalance.compareTo(amount) < 0) {
	            throw new RuntimeException("Insufficient balance");
	        }

	        BigDecimal newBalance = oldBalance.subtract(amount);
	        acc.setBalance(newBalance);

	        Transaction tx = buildTransaction(
	                TransactionType.Withdraw,
	                amount,
	                newBalance,
	                acc,
	                oldBalance,
	                ApprovalStatus.Completed
	        );

	        transRepo.save(tx);
	        accRepo.save(acc);

	        return "COMPLETED";

	    } else {

	        Transaction tx = buildTransaction(
	                TransactionType.Withdraw,
	                amount,
	                oldBalance,
	                acc,
	                oldBalance,
	                ApprovalStatus.Pending
	        );

	        transRepo.save(tx);

	        return "PENDING";
	    }
	}

	// version 2
	
	
//	@Override
//	public List<TransactionView> getTransactionView(int id) {
//		return transRepo.findTxByAccountId(id);
//	}

	@Override
	public List<TransactionView> getTransactionView(int id) {
		return transRepo.findTxByAccountId(id);
	}
	
	
	@Override
	public long getTransactionCount(int id) {
		return transRepo.countTxForAccount(id);
	}
	
	


	@Override
	public void approveWithdrawal(int transactionId) {
		Transaction tx = transRepo.findById(transactionId)
	            .orElseThrow(() -> new RuntimeException("Transaction not found"));

	    if (tx.getApprovalStatus() != ApprovalStatus.Pending) {
	        throw new RuntimeException("Transaction is not pending");
	    }

	    if (tx.getType() != TransactionType.Withdraw) {
	        throw new RuntimeException("Only withdrawals can be approved");
	    }

	    Account acc = tx.getAccount();

	    if (acc.getBalance().compareTo(tx.getAmount()) < 0) {
	        throw new RuntimeException("Insufficient balance at approval time");
	    }

	    BigDecimal newBalance = acc.getBalance().subtract(tx.getAmount());

	    acc.setBalance(newBalance);

	    tx.setBalanceAfter(newBalance);
	    tx.setApprovalStatus(ApprovalStatus.Completed);
	    tx.setApprovedBy(getLoggedInUserId());

	    accRepo.save(acc);
	    transRepo.save(tx);
	}



	@Override
	public List<Transaction> getPendingApprovals() {
	    return transRepo.findByApprovalStatus(ApprovalStatus.Pending);
	}

	private int getLoggedInUserId() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    User user = userRepo.findByUserName(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    return user.getId();
	}

	
	
	
	private Transaction buildTransaction(TransactionType type,BigDecimal amount, BigDecimal balanceAfter,Account account,BigDecimal balanceBefore, ApprovalStatus approvalStatus) {
		Transaction tx = new Transaction();
	    tx.setType(type);
	    tx.setAmount(amount);
	    tx.setBalanceBefore(balanceBefore);
	    tx.setBalanceAfter(balanceAfter);
	    tx.setApprovalStatus(approvalStatus);
	    tx.setPerformedBy(getLoggedInUserId()); //...............................
	    tx.setDoneAt(LocalDateTime.now());
	    tx.setAccount(account);
	    return tx;
	}




	}

