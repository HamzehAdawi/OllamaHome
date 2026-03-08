package com.HamzehAdawi.OllamaApp.services;

import com.HamzehAdawi.OllamaApp.tools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@RefreshScope
@Service
public class OllamaServiceImpl implements OllamaService {

    private final ChatClient chatClient;
    private String activeModel;

    @Autowired
    public OllamaServiceImpl(ChatClient.Builder builder) {
        ChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        MessageChatMemoryAdvisor advisor = MessageChatMemoryAdvisor.builder(memory).build();

        this.chatClient = builder
                .defaultAdvisors(advisor)
                .build();
    }

    public String chat(String userInput) {

        var prompt = chatClient.prompt()
          
                .system("You are a helpful assistant. Respond in natural language. Prefer plain code blocks for coding questions.")
                .user(userInput)
                .options(OllamaOptions.builder().model(activeModel).build());


        if (mentionsDateTime(userInput)) {
            prompt = prompt.tools(new DateTimeTools());
        }
        String response = prompt.call().content();

        if (isBrokenToolCallResponse(response)) {
            return "I'm trying to generate code, but something may have interfered. Please rephrase your request.";
        }

        return response;
    }

    private boolean mentionsDateTime(String input) {
        String lower = input.toLowerCase();
        return lower.contains("date") || lower.contains("time") || lower.contains("timezone");
    }

    private boolean isBrokenToolCallResponse(String response) {
        return response != null && response.contains("generateCode") && response.contains("parameters");
    }

    public String getActiveModel() {
        return activeModel;
    }

    public void setActiveModel(String activeModel) {
        this.activeModel = activeModel;
    }
}

