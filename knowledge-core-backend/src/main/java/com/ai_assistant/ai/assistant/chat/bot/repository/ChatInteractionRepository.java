package com.ai_assistant.ai.assistant.chat.bot.repository;


import com.ai_assistant.ai.assistant.chat.bot.model.ChatInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatInteractionRepository extends JpaRepository<ChatInteraction, Long> {
}