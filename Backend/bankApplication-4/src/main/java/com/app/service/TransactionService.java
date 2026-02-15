package com.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.app.entity.Transaction;
import com.app.repo.TransactionView;

public interface TransactionService {
  public void transfer(int fromAccId, int toAccId, BigDecimal amount);
  public void deposit(int accId, BigDecimal amount);
  public String withdraw(int accId, BigDecimal amount);
  List<TransactionView> getTransactionView(int id);
  long getTransactionCount(int id);
  void approveWithdrawal(int transactionId);
  List<Transaction> getPendingApprovals();
}
