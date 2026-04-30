package com.ai_assistant.ai.assistant.chat.bot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
@CrossOrigin(origins = "http://localhost:4000") // Change to 4000 if your React app is still on port 4000!
public class DocumentController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        // We will eventually forward this file to the Python LangChain service.
        // For now, we just verify Spring Boot received it successfully.
        String fileName = file.getOriginalFilename();
        System.out.println("Received file: " + fileName);

        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }
}