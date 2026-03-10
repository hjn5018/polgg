package com.polgg.polgg.service.ai;

import com.polgg.polgg.domain.application.ApplicationHistory;
import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.repository.application.ApplicationHistoryRepository;
import com.polgg.polgg.repository.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AICollectorService {

    private final PortfolioRepository portfolioRepository;
    private final ApplicationHistoryRepository applicationHistoryRepository;

    @Transactional(readOnly = true)
    public List<String> collectPublicDataForRAG() {
        List<String> collectedTexts = new ArrayList<>();

        // 1. 공개 포트폴리오 데이터 수집
        List<Portfolio> publicPortfolios = portfolioRepository.findAllByIsPublicTrueOrderByCreatedAtDesc();
        for (Portfolio portfolio : publicPortfolios) {
            String portfolioText = String.format(
                    "학생 포트폴리오 제목: %s\n자기소개: %s\n관리 링크: GitHub(%s), Blog(%s)\n연락처: %s\n",
                    portfolio.getTitle(),
                    portfolio.getIntroMessage(),
                    portfolio.getGithubUrl(),
                    portfolio.getBlogUrl(),
                    portfolio.getContact()
            );

            String projectText = portfolio.getProjects().stream()
                    .map(p -> String.format("- 프로젝트명: %s, 기간: %s, 담당 역할: %s, 내용: %s",
                            p.getName(), p.getPeriod(), p.getRole(), p.getContent()))
                    .collect(Collectors.joining("\n"));

            String certText = portfolio.getCertificates().stream()
                    .map(c -> String.format("- 보유 자격증: %s (취득일: %s)", c.getName(), c.getIssueDate()))
                    .collect(Collectors.joining("\n"));

            collectedTexts.add(portfolioText + "프로젝트 리스트:\n" + projectText + "\n자격증 리스트:\n" + certText);
        }

        // 2. 지원 기록 데이터 수집 (익명화 처리 고려)
        List<ApplicationHistory> histories = applicationHistoryRepository.findAll();
        for (ApplicationHistory history : histories) {
            String status = history.getStatus();
            String historyText = String.format(
                    "취업 지원 사례: %s 기업에 %s 경로로 %s 단계에서 %s 결과가 나왔습니다. (지원일: %s)",
                    history.getCompanyName(),
                    history.getChannel(),
                    history.getCategory(),
                    status,
                    history.getAppliedAt()
            );
            collectedTexts.add(historyText);
        }

        return collectedTexts;
    }
}
