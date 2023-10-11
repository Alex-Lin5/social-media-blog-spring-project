package com.example.controller;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.jboss.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    AccountService accountService;
    MessageService messageService;
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

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
    public ResponseEntity<Account> postAccountLogin(@RequestBody Account a){
        System.out.println("Handling login, input "+a);
        Account account = accountService.psProcessLogin(a);
        if(account == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> postNewMessage(@RequestBody Message m){
        Message message = messageService.psNewMessage(m);
        if(message == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.sGetAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

    // @GetMapping(value = "cats", params = {"term"})
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        Message message = messageService.sGetMessageById(message_id);
        if(message == null){
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable("message_id") Integer message_id){
        System.out.println("Handling message delete, "+ message_id);
        Message message = messageService.sDeleteMessageById(message_id);
        if(message == null){
            return ResponseEntity.status(HttpStatus.OK).body(0);
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);

    }
    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> patchMessageById(@PathVariable Integer message_id, @RequestBody Message m){
        m.setMessage_id(message_id);
        Message message = messageService.sPatchMessage(m);
        if(message == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(1);

    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer account_id){
        List<Message> messages = messageService.sGetMessagesByAccount(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

}
