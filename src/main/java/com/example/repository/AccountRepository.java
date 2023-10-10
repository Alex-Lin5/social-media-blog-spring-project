package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("FROM Account WHERE username = :usrn")
    Optional<Account> findByUsername(@Param("usrn") String username);

    // @Query("INSERT INTO Account()")
    // Integer insertAccount(@Param("usrn") String username, @Param("pswd") String password);
}
