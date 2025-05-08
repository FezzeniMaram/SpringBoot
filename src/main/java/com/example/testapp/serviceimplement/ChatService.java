package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Chat;
import com.example.testapp.entities.Conversation;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.ChatRepository;
import com.example.testapp.repository.ConversationRepository;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.ChatInterface;
import com.example.testapp.services.TuteurInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ChatService implements ChatInterface {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    TuteurRepository tuteurRepository;
    @Override
    public Chat envoyerMessage(Long conversationId, Chat chat) {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        if (conversation == null) return null;

        chat.setConversation(conversation);
        chat.setDateChat(LocalDateTime.now());

        String role = chat.getExpediteurRole();

        if ("ETUDIANT".equalsIgnoreCase(role)) {
            chat.setEtudiant(conversation.getEtudiant());
            chat.setTuteur(conversation.getTuteur()); // important si besoin d’afficher le tuteur dans le chat
        } else if ("TUTEUR".equalsIgnoreCase(role)) {
            chat.setTuteur(conversation.getTuteur());
            chat.setEtudiant(conversation.getEtudiant()); // aussi important
        }

        return chatRepository.save(chat);
    }




    @Override
    public List<Chat> getMessages(Long conversationId, String role) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation introuvable"));

        return chatRepository.findByConversationIdOrderByDateChatAsc(conversationId).stream()
                .filter(c -> {
                    if ("ETUDIANT".equalsIgnoreCase(role)) return c.isVisibleParEtudiant();
                    if ("TUTEUR".equalsIgnoreCase(role)) return c.isVisibleParTuteur();
                    return false;
                })
                .toList();
    }
    }
