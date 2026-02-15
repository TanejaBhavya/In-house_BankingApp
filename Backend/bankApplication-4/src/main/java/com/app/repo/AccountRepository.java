package com.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Account;
@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>{
		Optional<Account> findByAccountNum(String accountNum);
		
		// version2:
		
		@Query("""
		        select distinct a
		        from Account a
		        left join fetch a.transactions
		        where a.id = :id
		    """)
		    Optional<Account> findAccountWithTransactions(@Param("id") int id);

		
		@Query("""
			    select a.id as id,
			           a.userName as userName,
			           a.balance as balance
			    from Account a
			""")
			List<AccountSummary> findAccountSummaries();


		 
		 @Query("""
			        select a.id as id,
			               a.userName as userName,
			               a.balance as balance
			        from Account a
			        where a.id = :id
			    """)
			    Optional<AccountSummary> findAccountSummaryById(
			            @Param("id") int id
			    );
		 
		 
}
