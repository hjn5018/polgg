package com.polgg.polgg.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@office\\.kopo\\.ac\\.kr$", message = "한국폴리텍대학교 학교 이메일(@office.kopo.ac.kr)만 사용 가능합니다.")
    private String email;
}
