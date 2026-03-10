package com.polgg.polgg.dto.portfolio;

import com.polgg.polgg.domain.portfolio.Portfolio;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioResponseDto {
    private Long id;
    private String studentId;
    private String title;
    private String contact;
    private String githubUrl;
    private String blogUrl;
    private String introMessage;
    private boolean isMain;
    private boolean isPublic;
    private List<ProjectResponseDto> projects;
    private List<CertificateResponseDto> certificates;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PortfolioResponseDto from(Portfolio portfolio) {
        return PortfolioResponseDto.builder()
                .id(portfolio.getId())
                .studentId(portfolio.getMember().getStudentId())
                .title(portfolio.getTitle())
                .contact(portfolio.getContact())
                .githubUrl(portfolio.getGithubUrl())
                .blogUrl(portfolio.getBlogUrl())
                .introMessage(portfolio.getIntroMessage())
                .isMain(portfolio.isMain())
                .isPublic(portfolio.isPublic())
                .projects(portfolio.getProjects().stream().map(ProjectResponseDto::from).collect(Collectors.toList()))
                .certificates(portfolio.getCertificates().stream().map(CertificateResponseDto::from).collect(Collectors.toList()))
                .createdAt(portfolio.getCreatedAt())
                .updatedAt(portfolio.getUpdatedAt())
                .build();
    }
}
