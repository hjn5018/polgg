package com.polgg.polgg.dto.portfolio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDto {
    private String name;
    private String period;
    private String videoUrl;
    private int memberCount;
    private String role;
    private String content;
}
