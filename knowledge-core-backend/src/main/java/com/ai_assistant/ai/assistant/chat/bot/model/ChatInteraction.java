package com.ai_assistant.ai.assistant.chat.bot.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_interactions")
@Data // <-- Lombok handles getters and setters!
public class ChatInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String userPrompt;

    @Column(length = 5000)
    private String aiResponse;

    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}