package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping(value = "/register")
    public ResponseEntity<Account> postAccountRegistration(@RequestBody Account a){
        Optional<Account> optionalAccount = accountService.psAccountRegistration(a);
        if(optionalAccount.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } 
        if(optionalAccount.get().getAccount_id() == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalAccount.get());

    }
    @PostMapping(value = "/login")
    public void postAccountLogin(Account a){

    }
    @PostMapping(value = "/messages")
    public void postNewMessage(@RequestBody Message m){

    }
    @GetMapping("/messages")
    public void getAllMessages(){

    }
    // @GetMapping(value = "cats", params = {"term"})
    @GetMapping("/messages/{message_id}")
    public void getMessageById(@PathVariable Integer message_id){

    }
    @DeleteMapping(value = "/messages/{message_id}")
    public void deleteMessageById(@PathVariable Integer message_id){

    }
    @PatchMapping(value = "/messages/{message_id}")
    public void patchMessageById(@PathVariable Integer message_id){

    }
    @GetMapping(value = "/accounts/{account_id}")
    public void getMessagesByAccountId(@PathVariable Integer account_id){

    }

}
