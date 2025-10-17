package com.example.springaidemo.controller;

import com.example.springaidemo.service.SummarizerService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiController {

    private final ChatClient chatClient;
    private final SummarizerService summarizerService;

    public AiController(ChatClient.Builder builder, SummarizerService summarizerService) {
        this.chatClient = builder.build();
        this.summarizerService = summarizerService;
    }

    @GetMapping("/ask")
    public String askAi(@RequestParam String prompt) {
        return chatClient.prompt(prompt).call().content();
    }

    @GetMapping("/summarize")
    public String summarize(@RequestParam(value = "text") String textToSummarize) {
        return summarizerService.summarizeText(textToSummarize);
    }
}
