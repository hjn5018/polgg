package com.polgg.polgg.service.dashboard;

import com.polgg.polgg.domain.application.ApplicationHistory;
import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.dto.dashboard.DashboardItemDto;
import com.polgg.polgg.repository.application.ApplicationHistoryRepository;
import com.polgg.polgg.repository.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PortfolioRepository portfolioRepository;
    private final ApplicationHistoryRepository applicationHistoryRepository;

    @Transactional(readOnly = true)
    public List<DashboardItemDto> getPublicDashboard() {
        List<Portfolio> publicPortfolios = portfolioRepository.findAllByIsPublicTrueOrderByCreatedAtDesc();

        return publicPortfolios.stream().map(portfolio -> {
            String latestStatus = applicationHistoryRepository.findFirstByMemberOrderByAppliedAtDesc(portfolio.getMember())
                    .map(ApplicationHistory::getStatus)
                    .orElse("미지원");

            return DashboardItemDto.builder()
                    .portfolioId(portfolio.getId())
                    .title(portfolio.getTitle())
                    .studentName(portfolio.getMember().getName())
                    .studentId(maskStudentId(portfolio.getMember().getStudentId()))
                    .latestStatus(latestStatus)
                    .updatedDate(portfolio.getUpdatedAt())
                    .build();
        }).collect(Collectors.toList());
    }

    private String maskStudentId(String studentId) {
        if (studentId == null || studentId.length() < 5) return studentId;
        return studentId.substring(0, 4) + "****" + studentId.substring(8);
    }
}
