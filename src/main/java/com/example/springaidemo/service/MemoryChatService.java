package com.example.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoryChatService {

    private final ChatClient chatClient;
    // セッションメモリ用のMap
    private final Map<String, List<String>> sessionMemory = new ConcurrentHashMap<>();

    public MemoryChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String chat(String sessionId, String message) {
        // sessionIdに基づいて履歴を取得または新規作成
        List<String> history = sessionMemory.computeIfAbsent(sessionId, k -> new ArrayList<>());
        // ユーザーのメッセージを履歴に追加
        history.add("User: " + message);

        // 履歴を結合してコンテキスト文字列を作成
        String context = String.join("\n", history);
        // コンテキストを含んだプロンプトを作成
        Prompt prompt = new Prompt("Conversation:\n" + context + "\nAI:");

        // AIの返信を取得
        String aiReply = chatClient.prompt(prompt).call().content();
        // AIの返信を履歴に追加
        history.add("AI: " + aiReply);
        // AIの返信を返す
        return aiReply;
    }
}
