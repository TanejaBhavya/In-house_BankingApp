package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.ApprovalStatus;
import com.app.entity.Transaction;
import com.app.entity.TransactionType;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>{
	@Query("""
	        select t.type as type,
	               t.amount as amount,
	               t.balanceAfter as balanceAfter,
	               t.doneAt  as doneAt 
	        from Transaction t
	        where t.account.id = :accountId
	        order by t.doneAt  desc
	    """)
    List<TransactionView> findTxByAccountId(@Param("accountId") int accountId);
	
    List<Transaction> findByAccount_IdAndType(
            int accountId,
            TransactionType type
    );
  

	
	@Query("""
	        select count(t)
	        from Transaction t
	        where t.account.id = :accountId
	    """)
	    long countTxForAccount(@Param("accountId") int accountId);

	List<Transaction> findByApprovalStatus(ApprovalStatus pending);
	
	
}
