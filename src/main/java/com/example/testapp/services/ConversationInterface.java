package com.example.testapp.services;

import com.example.testapp.entities.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationInterface {
    Conversation getOrCreateConversation(Long etudiantId, Long tuteurId);
    List<Conversation> getUserConversations(Long userId, String role);
    void masquerConversation(Long conversationId, String role);

    Conversation getConversationById(Long id);
    boolean isBloque(Long conversationId, String role);
    void bloquerUtilisateur(Long conversationId, String role);
    public void debloquerUtilisateur(Long conversationId, String role);
    public List<Conversation> getBlockedConversations(Long userId, String role);

}
