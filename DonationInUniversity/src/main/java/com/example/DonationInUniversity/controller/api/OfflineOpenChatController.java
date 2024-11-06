package com.example.DonationInUniversity.controller.api;

import com.example.DonationInUniversity.model.MyCustomResponse;
import com.example.DonationInUniversity.model.OpenChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/offline_open_chat")
public class OfflineOpenChatController {

    private static final Logger logger = LoggerFactory.getLogger(OfflineOpenChatController.class);

    @Value("${openchat.url}")
    private String BASE_URL;

    @Value("${openchat.system_role}")
    private String BASE_ROLE;

    @Value("${openchat.system_content}")
    private String BASE_CONTENT;

    @Value("${openchat.model}")
    private String BASE_MODEL;

    @Value("${translate.url}")
    private String TRANSLATE_URL;

    @Async
    @PostMapping("/create_tags")
    public CompletableFuture<MyCustomResponse<String>> callOpenChatApi(@RequestBody Map<String, String> requestBody) {
        logger.info("Received request to /create_tags");

        // Extract the blog content from the incoming request body
        String blogContent = requestBody.get("content");
        logger.info("Extracted blog content: {}", blogContent);

        // Check if the blog content is empty or null
        if (blogContent == null || blogContent.trim().isEmpty()) {
            logger.warn("Invalid request: Blog content is missing");
            return CompletableFuture.completedFuture(
                    new MyCustomResponse<>(400, "Invalid request: Blog content is missing", null)
            );
        }

        // Translate the blog content to English
        String translatedContent;
        try {
            translatedContent = translateContent(blogContent, "en"); // Translate to English
        } catch (Exception e) {
            logger.error("Error occurred while translating content: {}", e.getMessage(), e);
            return CompletableFuture.completedFuture(
                    new MyCustomResponse<>(500, "Error occurred while translating content: " + e.getMessage(), null)
            );
        }

        logger.info("Translated content: {}", translatedContent);

        // Continue with the logic for calling the OpenChat API using the translated content
        Map<String, Object> openChatRequestBody = new HashMap<>();
        openChatRequestBody.put("model", BASE_MODEL);

        // Construct the messages part of the request using a List
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", BASE_ROLE);
        systemMessage.put("content", BASE_CONTENT);

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", "Here is the blog content: [" + translatedContent + "]");

        openChatRequestBody.put("messages", List.of(systemMessage, userMessage));
        openChatRequestBody.put("stream", false);

        logger.info("Constructed OpenChat request body: {}", openChatRequestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(openChatRequestBody, headers);
        logger.info("Prepared HTTP entity: {}", entity);

        RestTemplate restTemplate = new RestTemplate();

        try {
            logger.info("Calling external API: {}", BASE_URL + "/api/chat");
            ResponseEntity<OpenChatResponse> response = restTemplate.exchange(
                    BASE_URL + "/api/chat",
                    HttpMethod.POST,
                    entity,
                    OpenChatResponse.class
            );

            logger.info("Received response from OpenChat API: {}", response);

            if (response.getBody() != null && response.getBody().getMessage() != null) {
                String tags = response.getBody().getMessage().getContent();
                logger.info("Extracted tags: {}", tags);

                // Translate the extracted tags to Vietnamese
                String translatedTags;
                try {
                    translatedTags = translateContent(tags, "vi"); // Translate to Vietnamese
                } catch (Exception e) {
                    logger.error("Error occurred while translating tags to Vietnamese: {}", e.getMessage(), e);
                    return CompletableFuture.completedFuture(
                            new MyCustomResponse<>(500, "Error occurred while translating tags: " + e.getMessage(), null)
                    );
                }

                logger.info("Translated tags to Vietnamese: {}", translatedTags);
                return CompletableFuture.completedFuture(new MyCustomResponse<>(200, "Success create tags", translatedTags));
            } else {
                logger.warn("Failed to extract tags from response");
                return CompletableFuture.completedFuture(new MyCustomResponse<>(500, "Failed to extract tags from response", null));
            }

        } catch (Exception e) {
            logger.error("Error occurred while calling the external API: {}", e.getMessage(), e);
            return CompletableFuture.completedFuture(new MyCustomResponse<>(500, "Error occurred while calling the external API: " + e.getMessage(), null));
        }
    }


    private String translateContent(String content, String targetLang) throws Exception {
        logger.info("Translating content: {} to language: {}", content, targetLang);

        // Create the request body for the translation service
        Map<String, String> translateRequestBody = new HashMap<>();
        translateRequestBody.put("text", content);
        translateRequestBody.put("targetLang", targetLang); // Dynamic target language

        // Set up headers for the translation service
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(translateRequestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            logger.info("Calling translation API: {}", TRANSLATE_URL + "/translate");
            ResponseEntity<Map> response = restTemplate.exchange(
                    TRANSLATE_URL + "/translate",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            logger.info("Received response from translation API: {}", response);

            // Extract the translated text from the response
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String translatedText = (String) response.getBody().get("translatedText");
                logger.info("Translated text: {}", translatedText);
                return translatedText;
            } else {
                logger.warn("Translation API returned an error: {}", response);
                throw new Exception("Failed to translate content.");
            }

        } catch (Exception e) {
            logger.error("Error occurred while calling translation service: {}", e.getMessage(), e);
            throw new Exception("Translation service error: " + e.getMessage());
        }
    }

}
