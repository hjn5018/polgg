package com.polgg.polgg.dto.portfolio;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioRequestDto {
    private String title;
    private String contact;
    private String githubUrl;
    private String blogUrl;
    private String introMessage;
    private boolean isMain;
    private boolean isPublic;
    private List<ProjectRequestDto> projects;
    private List<CertificateRequestDto> certificates;
}
