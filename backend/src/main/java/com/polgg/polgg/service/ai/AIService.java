package com.polgg.polgg.service.ai;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {

    private final AICollectorService collectorService;

    @Value("${langchain4j.ollama.chat-model.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${langchain4j.ollama.chat-model.model-name:llama3}")
    private String chatModelName;

    @Value("${langchain4j.ollama.embedding-model.model-name:nomic-embed-text}")
    private String embeddingModelName;

    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private OllamaChatModel chatModel;

    @PostConstruct
    public void init() {
        // 로컬 LLM (Ollama) 설정
        this.embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .modelName(embeddingModelName)
                .build();

        this.embeddingStore = new InMemoryEmbeddingStore<>();

        this.chatModel = OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(chatModelName)
                .timeout(Duration.ofSeconds(60))
                .build();
    }

    /**
     * 공개 데이터를 수집하여 임베딩하고 저장소에 저장합니다.
     */
    public void trainRAG() {
        log.info("로컬 RAG 데이터 학습 시작 (Ollama 사용)...");
        List<String> data = collectorService.collectPublicDataForRAG();
        
        for (String text : data) {
            TextSegment segment = TextSegment.from(text);
            embeddingStore.add(embeddingModel.embed(segment).content(), segment);
        }
        log.info("{} 개의 데이터 조각이 로컬 임베딩되었습니다.", data.size());
    }

    /**
     * 사용자 질문에 대해 RAG 기반 답변을 생성합니다.
     */
    public String ask(String userQuery) {
        // 1. 질문 임베딩 및 유사 데이터 검색
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(
                embeddingModel.embed(userQuery).content(), 3);

        String context = relevant.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n\n---\n\n"));

        // 2. 프롬프트 생성 및 로컬 LLM 요청
        String prompt = String.format(
                "당신은 IT 취업 포털 POL.GG의 취업 컨설턴트 챗봇입니다.\n" +
                "아래 제공된 [추출된 데이터 컨텍스트]를 바탕으로 사용자의 질문에 친절하게 답변하세요.\n" +
                "학생들의 실제 포트폴리오 사례와 합불 이력을 분석하여 현실적인 조언을 해주세요.\n" +
                "반드시 한국어로 답변하세요.\n\n" +
                "[추출된 데이터 컨텍스트]:\n%s\n\n" +
                "[사용자 질문]: %s",
                context, userQuery
        );

        return chatModel.generate(prompt);
    }
}
