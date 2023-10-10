package com.example.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    AccountRepository accountRepository;
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message psNewMessage(Message m){
        if(m == null) return null;
        int len = m.getMessage_text().length();
        if(len<=0 || len>=255){
            return null;
        }
        Optional<Account> validUser = accountRepository.findById(m.getPosted_by());
        if(validUser.isPresent()){
            return messageRepository.save(m);
        }
        return null;
    }

    public List<Message> sGetAllMessages(){
        return null;
    }
}
