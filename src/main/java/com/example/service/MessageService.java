package com.example.service;

import java.util.Optional;
import java.util.ArrayList;
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
        List<Message> messages = messageRepository.findAll();        
        return messages;
    }

    public Message sGetMessageById(Integer message_id){
        System.out.println("Deleting message, "+message_id);
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        System.out.println("Message cannot find, will not delete it. "+message_id);
        return null;
    }

    public Message sDeleteMessageById(Integer message_id){
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent()){
            messageRepository.deleteById(message_id);
            return optionalMessage.get();
        }
        return null;
    }

    public Message sPatchMessage(Message m){
        int len = m.getMessage_text().length();
        if(len<=0 || len>=255){
            return null;
        }
        Optional<Message> optionalMessage = messageRepository.findById(m.getMessage_id());
        if(optionalMessage.isEmpty()){
            return null;
        }
        Message message = new Message(
            m.getMessage_id(),
            m.getPosted_by(),
            m.getMessage_text(),
            m.getTime_posted_epoch()
        );
        return messageRepository.save(message);
    }

    public List<Message> sGetMessagesByAccount(Integer account_id){
        Optional<Account> optionalAccount = accountRepository.findById(account_id);
        if(optionalAccount.isEmpty()){
            return null;
        }
        List<Message> messages = messageRepository.findAllByAccount(account_id);
        return messages;
    }
}
