package com.polgg.polgg.dto.application;

import com.polgg.polgg.domain.application.ApplicationHistory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDto {
    private Long id;
    private String companyName;
    private String channel;
    private String category;
    private String status;
    private LocalDateTime appliedAt;
    private Long portfolioId; // 제출한 포트폴리오 ID
    private String portfolioTitle; // 포트폴리오 제목 (선택 시 정보 제공용)

    public static ApplicationResponseDto from(ApplicationHistory applicationHistory) {
        return ApplicationResponseDto.builder()
                .id(applicationHistory.getId())
                .companyName(applicationHistory.getCompanyName())
                .channel(applicationHistory.getChannel())
                .category(applicationHistory.getCategory())
                .status(applicationHistory.getStatus())
                .appliedAt(applicationHistory.getAppliedAt())
                .portfolioId(applicationHistory.getPortfolio() != null ? applicationHistory.getPortfolio().getId() : null)
                .portfolioTitle(applicationHistory.getPortfolio() != null ? applicationHistory.getPortfolio().getTitle() : null)
                .build();
    }
}
