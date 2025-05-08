package com.example.testapp.services;

import com.example.testapp.entities.Chat;

import java.util.List;


public interface ChatInterface {
    Chat envoyerMessage(Long conversationId, Chat chat);
    List<Chat> getMessages(Long conversationId, String role);
}
