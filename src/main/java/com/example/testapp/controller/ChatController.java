package com.example.testapp.controller;

import com.example.testapp.entities.Chat;
import com.example.testapp.services.ChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatInterface chatInterface;

    @GetMapping("/add")
    public Chat addChat(@RequestBody Chat chat){
        return chatInterface.addChat(chat);
    }
    @GetMapping("/getAllChat")
    public List<Chat> getAll(Chat chat){
        return chatInterface.getChat(chat);
    }
}
