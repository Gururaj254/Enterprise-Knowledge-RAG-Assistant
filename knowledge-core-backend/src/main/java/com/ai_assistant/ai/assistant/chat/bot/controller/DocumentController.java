package com.ai_assistant.ai.assistant.chat.bot.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents") // Clean, separate route!
@CrossOrigin(origins = "http://localhost:4000")
public class DocumentController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            String pythonUploadUrl = "http://localhost:8000/api/ai/upload";

            ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileAsResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    pythonUploadUrl,
                    requestEntity,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            System.err.println("Upload Error: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"message\": \"Failed to upload file to AI service.\"}");
        }
    }
}