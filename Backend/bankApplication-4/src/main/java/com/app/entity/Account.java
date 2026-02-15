package com.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column (nullable = true, unique = true, length = 12)
	private String accountNum;
	
	// user details 
	@Column(nullable = false)
	private String userName;
	private String emailId;
	private String phoneNum;
	@Column(nullable = false, precision = 15,scale=2)
	private BigDecimal balance;
	
	// creation detail
	@Column(nullable = true)
	private int createdById;
	
	@CreationTimestamp
	@Column(nullable = true)
	private LocalDateTime  createdAt;
	

	
	public Account(String userName, String emailId, BigDecimal balance) {
		this.userName = userName;
		this.emailId = emailId;
		this.balance = balance;
	}

	
	// version2 changes :
	
	// joining tables
	



	public Account(String userName, BigDecimal balance, String emailId, String phoneNum) {
		this.userName = userName;
		this.emailId = emailId;
		this.balance = balance;
		this.phoneNum = phoneNum;
	}


	@JsonManagedReference
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
	private List<Transaction> transactions = new ArrayList<>();
}
