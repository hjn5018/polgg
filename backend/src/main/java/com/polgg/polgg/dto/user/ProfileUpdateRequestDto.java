package com.polgg.polgg.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequestDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private boolean isPortfolioPublic;
}
