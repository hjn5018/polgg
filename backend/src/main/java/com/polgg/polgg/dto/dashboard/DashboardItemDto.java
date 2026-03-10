package com.polgg.polgg.dto.dashboard;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardItemDto {
    private Long portfolioId;
    private String title;
    private String studentName;
    private String studentId;
    private String latestStatus; // "합격", "진행중", "불합격" 등
    private LocalDateTime updatedDate;
}
