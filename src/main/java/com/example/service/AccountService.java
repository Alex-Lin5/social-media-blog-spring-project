package com.example.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
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
        Optional<Account> optionalAccount = accountRepository.findByUsername(a.getUsername());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setAccount_id(null);
            System.out.println("Duplicated username."+ a);
            return Optional.of(account);
        } else{
            return persistAccount(a);
        }
    }

    public Account psProcessLogin(Account a){
        Optional<Account> optionalAccount = accountRepository.findByUsername(a.getUsername());
        System.out.println("Processing login now... Input account "+a);
        if(optionalAccount.isEmpty()){
            System.out.println("Username cannot find.");
            return null;
        }
        String databasePW = optionalAccount.get().getPassword();
        String inputPW = a.getPassword();
        if(databasePW.equals(inputPW)){
            Account account = new Account(
                optionalAccount.get().getAccount_id(),
                a.getUsername(),
                a.getPassword()
            );
            System.out.println("Login verified. "+account);
            return account;
        } else{
            System.out.println("Password does not match."+databasePW+' '+inputPW);
            return null;
        }

    }

}
