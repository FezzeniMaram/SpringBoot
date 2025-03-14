package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Chat;
import com.example.testapp.repository.ChatRepository;
import com.example.testapp.services.ChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService implements ChatInterface {
    @Autowired
    ChatRepository chatRepository;
    @Override
    public Chat addChat(Chat chat) {return chatRepository.save(chat);}

    @Override
    public List<Chat> getChat(Chat chat) {
        return chatRepository.findAll() ;
    }
}
