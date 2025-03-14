package com.example.testapp.repository;

import com.example.testapp.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface ChatRepository extends JpaRepository <Chat, Long> {
}
