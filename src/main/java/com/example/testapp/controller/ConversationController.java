package com.example.testapp.controller;

import com.example.testapp.entities.Conversation;
import com.example.testapp.repository.ConversationRepository;
import com.example.testapp.services.ConversationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/conversation")
@CrossOrigin("*")
public class ConversationController {

    @Autowired
    private ConversationInterface conversationInterface;
    @Autowired
    private ConversationRepository conversationRepository;


    @PostMapping("/start")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Conversation> getOrCreate(@RequestParam Long etudiantId,
                                                    @RequestParam Long tuteurId) {
        return ResponseEntity.ok(conversationInterface.getOrCreateConversation(etudiantId, tuteurId));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<List<Conversation>> getConversations(@PathVariable Long userId,
                                                               @RequestParam String role) {
        List<Conversation> list = conversationInterface.getUserConversations(userId, role);
        System.out.println("📦 Liste des conversations renvoyées : " + list.size());
        return ResponseEntity.ok(list);
    }


    @PostMapping("/{conversationId}/masquer")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Void> masquerConversation(@PathVariable Long conversationId,
                                                    @RequestParam String role) {
        conversationInterface.masquerConversation(conversationId, role);
        return ResponseEntity.ok().build();
    }



    // ✅ Nouvelle méthode pour récupérer une conversation par ID
    @GetMapping("/{conversationId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Conversation> getConversationById(@PathVariable Long conversationId) {
        Conversation conversation = conversationInterface.getConversationById(conversationId);
        return ResponseEntity.ok(conversation);
    }


    @PostMapping("/{conversationId}/bloquer")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Void> bloquerUtilisateur(@PathVariable Long conversationId,
                                                   @RequestParam String role) {
        conversationInterface.bloquerUtilisateur(conversationId, role);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/check-block/{conversationId}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Map<String, Boolean>> checkIfBlocked(@PathVariable Long conversationId,
                                                               @RequestParam String role) {
        boolean bloque = conversationInterface.isBloque(conversationId, role);
        return ResponseEntity.ok(Collections.singletonMap("bloque", bloque));
    }

    @PutMapping("/debloquer/{id}")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<Map<String, Object>> debloquerUtilisateur(
            @PathVariable Long id,
            @RequestParam String role) {

        conversationInterface.debloquerUtilisateur(id, role);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "✅ Utilisateur débloqué avec succès.");

        return ResponseEntity.ok(response); // ✅ ça fonctionne maintenant
    }


    @GetMapping("/blocked")
    @PreAuthorize("hasAnyAuthority('ETUDIANT', 'TUTEUR')")
    public ResponseEntity<?> getBlockedConversations(
            @RequestParam("userId") Long userId,
            @RequestParam("role") String role
    ) {
        System.out.println("UserId: " + userId + " | Role: " + role);

        List<Conversation> blockedConversations = new ArrayList<>();

        if (role.equalsIgnoreCase("ETUDIANT")) {
            blockedConversations = conversationRepository.findByEtudiant_IdEtudiantAndEtudiantBloqueTuteurTrue(userId);
        } else if (role.equalsIgnoreCase("TUTEUR")) {
            blockedConversations = conversationRepository.findByTuteur_IdTuteurAndTuteurBloqueEtudiantTrue(userId);
        } else {
            return ResponseEntity.badRequest().body("Rôle invalide. Doit être ETUDIANT ou TUTEUR.");
        }

        return ResponseEntity.ok(blockedConversations);
    }


}
