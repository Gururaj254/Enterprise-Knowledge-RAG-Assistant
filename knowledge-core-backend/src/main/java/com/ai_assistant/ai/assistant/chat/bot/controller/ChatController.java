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
@CrossOrigin(origins = "http://localhost:4000") // Allows React to talk to this API
public class ChatController {

    @Autowired
    private ChatInteractionRepository repository;

    // This helps Spring Boot make HTTP requests to your Python microservice
    private final RestTemplate restTemplate = new RestTemplate();

    // Endpoint to save a message and get the AI response
    @PostMapping("/save")
    public ChatInteraction saveInteraction(@RequestBody ChatInteraction interaction) {

        try {
            // 1. The URL of your Python FastAPI server
            String pythonApiUrl = "http://localhost:8000/api/chat";

            // 2. Package the user's question into a JSON format Python expects
            Map<String, String> requestBody = Map.of("question", interaction.getUserPrompt());

            // 3. Send the request to Python and wait for the response
            ResponseEntity<Map> response = restTemplate.postForEntity(pythonApiUrl, requestBody, Map.class);

            // 4. Extract the answer from Python's response
            String aiAnswer = (String) response.getBody().get("answer");

            // 5. Set the real AI answer into our database model
            interaction.setAiResponse(aiAnswer);

        } catch (Exception e) {
            System.err.println("Error calling Python AI Service: " + e.getMessage());
            interaction.setAiResponse("Sorry, the AI service is offline. Is the Python server running on port 8000?");
        }

        // Save the whole interaction (Prompt + Real AI Response) to MySQL
        return repository.save(interaction);
    }

    // Endpoint to load chat history
    @GetMapping("/history")
    public List<ChatInteraction> getHistory() {
        return repository.findAll();
    }
}