package com.example.testapp.serviceimplement;

import com.example.testapp.entities.Conversation;
import com.example.testapp.entities.Etudiant;
import com.example.testapp.entities.Tuteur;
import com.example.testapp.repository.ConversationRepository;
import com.example.testapp.repository.EtudiantRepository;
import com.example.testapp.repository.TuteurRepository;
import com.example.testapp.services.ConversationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService implements ConversationInterface {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private TuteurRepository tuteurRepository;
    @Override
    public Conversation getOrCreateConversation(Long etudiantId , Long tuteurId) {
        return conversationRepository.findByEtudiant_IdEtudiantAndTuteur_IdTuteur(etudiantId , tuteurId)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    Etudiant etu = etudiantRepository.findById(etudiantId).orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
                    Tuteur tut = tuteurRepository.findById(tuteurId).orElseThrow(() -> new RuntimeException("Tuteur introuvable"));

                    conversation.setEtudiant(etu);
                    conversation.setTuteur(tut);

                    return conversationRepository.save(conversation);
                });
    }

    @Override
    public List<Conversation> getUserConversations(Long userId, String role) {
        List<Conversation> conversations;

        if ("ETUDIANT".equalsIgnoreCase(role)) {
            conversations = conversationRepository.findByEtudiant_IdEtudiant(userId);
        } else if ("TUTEUR".equalsIgnoreCase(role)) {
            conversations = conversationRepository.findByTuteur_IdTuteur(userId);
        } else {
            return List.of();
        }

        return conversations.stream()
                .filter(conv -> {
                    if ("ETUDIANT".equalsIgnoreCase(role) && !conv.isVisibleParEtudiant()) return false;
                    if ("TUTEUR".equalsIgnoreCase(role) && !conv.isVisibleParTuteur()) return false;


                    if ("ETUDIANT".equalsIgnoreCase(role) && conv.isTuteurBloqueEtudiant()) return false;
                    if ("TUTEUR".equalsIgnoreCase(role) && conv.isEtudiantBloqueTuteur()) return false;

                    return true;
                })
                .peek(conv -> {
                    var messages = conv.getMessages();
                    if (messages != null && !messages.isEmpty()) {
                        String last = messages.stream()
                                .filter(msg -> {
                                    if ("ETUDIANT".equalsIgnoreCase(role)) return msg.isVisibleParEtudiant();
                                    if ("TUTEUR".equalsIgnoreCase(role)) return msg.isVisibleParTuteur();
                                    return false;
                                })
                                .reduce((first, second) -> second)
                                .map(chat -> chat.getMessageChat())
                                .orElse("");

                        conv.setLastMessage(last);
                    } else {
                        conv.setLastMessage("");
                    }
                })
                .toList();
    }




    @Override
    public void masquerConversation(Long conversationId, String role) {
        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation introuvable"));

        if ("ETUDIANT".equalsIgnoreCase(role)) {
            conv.setVisibleParEtudiant(false);
        } else if ("TUTEUR".equalsIgnoreCase(role)) {
            conv.setVisibleParTuteur(false);
        }

        conversationRepository.save(conv);
    }



    @Override
    public Conversation getConversationById(Long conversationId) {
        return conversationRepository.findById(conversationId).orElse(null);
    }

    @Override
    public void bloquerUtilisateur(Long conversationId, String role) {
        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation introuvable"));

        if ("ETUDIANT".equalsIgnoreCase(role)) {
            conv.setEtudiantBloqueTuteur(true);
        } else if ("TUTEUR".equalsIgnoreCase(role)) {
            conv.setTuteurBloqueEtudiant(true);
        }

        conversationRepository.save(conv);
    }

    @Override
    public void debloquerUtilisateur(Long conversationId, String role) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation introuvable"));

        if ("ETUDIANT".equalsIgnoreCase(role)) {
            conversation.setEtudiantBloqueTuteur(false);
        } else if ("TUTEUR".equalsIgnoreCase(role)) {
            conversation.setTuteurBloqueEtudiant(false);
        }

        conversationRepository.save(conversation);
    }
    public List<Conversation> getBlockedConversations(Long userId, String role) {
        if (role.equalsIgnoreCase("ETUDIANT")) {
            return conversationRepository.findByEtudiant_IdEtudiantAndEtudiantBloqueTuteurTrue(userId);
        } else if (role.equalsIgnoreCase("TUTEUR")) {
            return conversationRepository.findByTuteur_IdTuteurAndTuteurBloqueEtudiantTrue(userId);
        }
        return new ArrayList<>();
    }


    @Override
    public boolean isBloque(Long conversationId, String role) {
        Conversation conv = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation introuvable"));

        return conv.isEtudiantBloqueTuteur() || conv.isTuteurBloqueEtudiant();
    }

}

