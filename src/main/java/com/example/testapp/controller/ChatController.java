package com.example.testapp.controller;

import com.example.testapp.entities.Chat;
import com.example.testapp.repository.ChatRepository;
import com.example.testapp.services.ChatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatInterface chatInterface;
    @Autowired
    private ChatRepository chatRepository;


    @PostMapping("/envoyer/{conversationId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Chat> envoyerMessage(@PathVariable Long conversationId,
                                               @RequestBody Chat chat) {
        return ResponseEntity.ok(chatInterface.envoyerMessage(conversationId, chat));
    }

    @GetMapping("/conversation/{conversationId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<List<Chat>> getMessages(@PathVariable Long conversationId,
                                                  @RequestParam String role) {
        return ResponseEntity.ok(chatInterface.getMessages(conversationId, role));
    }
    @GetMapping("/conversation/all/{conversationId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public List<Chat> getAllMessagesRaw(@PathVariable Long conversationId) {
        return chatRepository.findByConversationIdOrderByDateChatAsc(conversationId);
    }
}
