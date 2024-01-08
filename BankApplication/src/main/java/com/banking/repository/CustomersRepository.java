package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.entity.Admin;
import com.banking.entity.Customers;

import jakarta.transaction.Transactional;

public interface CustomersRepository extends JpaRepository<Customers, Integer> {

	@Query("select c from Customers c where c.username=:username ")
	public Customers getCustomersByUsername(@Param("username") String username);
	
	@Query("select c from Customers c where c.password=:password ")
	public Customers getCustomersByPassword(@Param("password") String password);
	
	@Transactional
	@Query(value="delete c from Customers c where c.account_no=:account_no",nativeQuery = true)
	@Modifying
	public int deleteCustomersByAccountNo(@Param("account_no") int account_no);
	
}
