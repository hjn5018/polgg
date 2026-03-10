package com.polgg.polgg.service.ai;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {

    private final AICollectorService collectorService;

    @Value("${langchain4j.open-ai.chat-model.api-key:demo}")
    private String apiKey;

    private EmbeddingModel embeddingModel;
    private EmbeddingStore<TextSegment> embeddingStore;
    private OpenAiChatModel chatModel;

    @PostConstruct
    public void init() {
        this.embeddingModel = OpenAiEmbeddingModel.builder()
                .apiKey(apiKey)
                .modelName("text-embedding-3-small")
                .build();

        this.embeddingStore = new InMemoryEmbeddingStore<>();

        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4o")
                .build();
    }

    public void trainRAG() {
        log.info("RAG 데이터 학습 시작...");
        List<String> data = collectorService.collectPublicDataForRAG();
        
        for (String text : data) {
            TextSegment segment = TextSegment.from(text);
            embeddingStore.add(embeddingModel.embed(segment).content(), segment);
        }
        log.info("{} 개의 데이터 조각이 임베딩되었습니다.", data.size());
    }

    public String ask(String userQuery) {
        List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(
                embeddingModel.embed(userQuery).content(), 3);

        String context = relevant.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n\n---\n\n"));

        String prompt = String.format(
                "당신은 IT 취업 포털 POL.GG의 취업 컨설턴트 챗봇입니다.\n" +
                "아래 제공된 [추출된 데이터 컨텍스트]를 바탕으로 사용자의 질문에 친절하게 답변하세요.\n" +
                "학생들의 실제 포트폴리오 사례와 합불 이력을 분석하여 현실적인 조언을 해주세요.\n\n" +
                "[추출된 데이터 컨텍스트]:\n%s\n\n" +
                "[사용자 질문]: %s",
                context, userQuery
        );

        return chatModel.generate(prompt);
    }
}
