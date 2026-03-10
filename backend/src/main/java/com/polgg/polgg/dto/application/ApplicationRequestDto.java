package com.polgg.polgg.dto.application;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequestDto {
    private String companyName;
    private String channel;
    private String category;
    private String status;
    private LocalDateTime appliedAt;
    private Long portfolioId; // 제출한 포트폴리오 ID
}
