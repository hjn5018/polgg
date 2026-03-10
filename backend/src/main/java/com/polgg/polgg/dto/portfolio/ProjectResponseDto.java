package com.polgg.polgg.dto.portfolio;

import com.polgg.polgg.domain.portfolio.Project;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String period;
    private String videoUrl;
    private int memberCount;
    private String role;
    private String content;

    public static ProjectResponseDto from(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .period(project.getPeriod())
                .videoUrl(project.getVideoUrl())
                .memberCount(project.getMemberCount())
                .role(project.getRole())
                .content(project.getContent())
                .build();
    }
}
