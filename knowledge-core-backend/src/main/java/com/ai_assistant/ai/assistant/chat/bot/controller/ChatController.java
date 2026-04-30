package com.ai_assistant.ai.assistant.chat.bot.controller;

import com.ai_assistant.ai.assistant.chat.bot.model.ChatInteraction;
import com.ai_assistant.ai.assistant.chat.bot.repository.ChatInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000") // Allows React to talk to this API
public class ChatController {

    @Autowired
    private ChatInteractionRepository repository;

    // Endpoint to save a message
    @PostMapping("/save")
    public ChatInteraction saveInteraction(@RequestBody ChatInteraction interaction) {
        // Mocking the AI response for now until we connect the LangChain Python service
        interaction.setAiResponse("This is a temporary response. LangChain connection coming soon!");
        return repository.save(interaction);
    }

    // Endpoint to load chat history
    @GetMapping("/history")
    public List<ChatInteraction> getHistory() {
        return repository.findAll();
    }
}