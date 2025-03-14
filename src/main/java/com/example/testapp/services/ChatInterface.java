package com.example.testapp.services;

import com.example.testapp.entities.Chat;

import java.util.List;


public interface ChatInterface {
    public Chat addChat(Chat chat);
    public List<Chat> getChat(Chat chat);
}
