package com.polgg.polgg.controller.ai;

import com.polgg.polgg.service.ai.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIChatController {

    private final AIService aiService;

    /**
     * RAG 학습용 API (관리자용)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/train")
    public ResponseEntity<?> train() {
        aiService.trainRAG();
        return ResponseEntity.ok(Map.of("message", "RAG 데이터 학습이 완료되었습니다."));
    }

    /**
     * AI 취업 컨설턴트 챗봇 API
     */
    @GetMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestParam String query) {
        String answer = aiService.ask(query);
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
