package com.polgg.polgg.dto.user;

import com.polgg.polgg.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private String studentId;
    private String email;
    private String name;
    private UserRole role;
    private boolean isPortfolioPublic;
}
