package com.polgg.polgg.controller.ai;

import com.polgg.polgg.service.ai.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIChatController {

    private final AIService aiService;

    @PostMapping("/train")
    public ResponseEntity<?> train() {
        aiService.trainRAG();
        return ResponseEntity.ok(Map.of("message", "RAG 데이터 학습이 완료되었습니다."));
    }

    @GetMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestParam String query) {
        String answer = aiService.ask(query);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
