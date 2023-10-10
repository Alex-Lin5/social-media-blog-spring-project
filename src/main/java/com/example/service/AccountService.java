package com.example.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    // @Autowired
    // public AccountService(AccountRepository accountRepository){
    //     this.accountRepository = accountRepository;
    // }
    // public AccountService(){
    //     // accountRepository = new AccountRepository();
    // }
    private Optional<Account> persistAccount(Account account){
        Optional<Account> optionalAccount = Optional.of(accountRepository.save(account));
        return optionalAccount;
    }
    
    public Optional<Account> psAccountRegistration(Account a){
        if(a.getPassword().length()<4){
            System.out.println("password length should be at least 4 chcaracters long."+ a);
            return null;
        }
        if(a==null){
            System.out.println("Null account detected.");
            return null;
        }
        if(accountRepository==null){
            System.out.println("Account Repository is null.");
            return null;
        }
        // Account optionalAccount = accountRepository.findByUsername(a.getUsername());
        Optional<Account> optionalAccount = accountRepository.findByUsername(a.getUsername());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setAccount_id(null);
            System.out.println("Duplicated username."+ a);
            // throw new SQLException("Duplicated username."+a);
            // return a;
            return Optional.of(account);
        } else{
            // int account_id = accountRepository.insertAccount(a.getUsername(), a.getPassword());
            // Account account = new Account(
            //     account_id,
            //     a.getUsername(),
            //     a.getPassword());
            // return account;
            return persistAccount(a);
            // return accountRepository.save(a);
        }
        // return null;
    }
}
