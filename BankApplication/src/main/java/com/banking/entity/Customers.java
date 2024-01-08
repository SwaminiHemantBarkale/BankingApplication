package com.banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Customers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountNo;
	
	@Column
	private String name;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private int balance;
	
	@ManyToOne
	private Admin admin;

	public Customers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customers(int accountNo, String name, String username, String password, int balance, Admin admin) {
		super();
		this.accountNo = accountNo;
		this.name = name;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.admin = admin;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "Customers [accountNo=" + accountNo + ", name=" + name + ", username=" + username + ", password="
				+ password + ", balance=" + balance + ", admin=" + admin + "]";
	}
	
	
	
	

}
