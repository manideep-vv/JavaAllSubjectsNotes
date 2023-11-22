package com.demo.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> { 
    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Optional<Account> findByAccountNumber(String accountNumber);
}
