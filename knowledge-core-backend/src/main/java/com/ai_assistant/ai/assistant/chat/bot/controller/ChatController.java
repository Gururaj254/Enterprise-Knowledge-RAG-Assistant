package com.ai_assistant.ai.assistant.chat.bot.controller;

import com.ai_assistant.ai.assistant.chat.bot.model.ChatInteraction;
import com.ai_assistant.ai.assistant.chat.bot.repository.ChatInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:4000")
public class ChatController {

    @Autowired
    private ChatInteractionRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/save")
    public ChatInteraction saveInteraction(@RequestBody ChatInteraction interaction) {
        try {
            String pythonApiUrl = "http://localhost:8000/api/ai/chat";
            Map<String, String> requestBody = Map.of("question", interaction.getUserPrompt());

            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, requestBody, Map.class);
            String aiAnswer = (String) response.getBody().get("answer");

            interaction.setAiResponse(aiAnswer);
        } catch (Exception e) {
            System.err.println("Error calling Python AI Service: " + e.getMessage());
            interaction.setAiResponse("Sorry, the AI service is offline. Is the Python server running on port 8000?");
        }
        return repository.save(interaction);
    }

    @GetMapping("/history")
    public List<ChatInteraction> getHistory() {
        return repository.findAll();
    }

    // CLEAR CHAT ENDPOINT ---
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearHistory() {
        repository.deleteAll();
        return ResponseEntity.ok("Chat history cleared.");
    }
}