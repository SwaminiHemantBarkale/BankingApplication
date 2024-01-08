package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.banking.entity.Admin;


public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	@Query("select a from Admin a where a.username=:username ")
	public Admin getAdminByUsername(@Param("username") String username);
	
	@Query("select a from Admin a where a.password=:password ")
	public Admin getAdminByPassword(@Param("password") String password);

}
