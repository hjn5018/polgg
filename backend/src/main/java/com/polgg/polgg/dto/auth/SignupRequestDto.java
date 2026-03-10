package com.polgg.polgg.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @NotBlank(message = "학번은 필수 입력 값입니다.")
    @Pattern(regexp = "^\\d{10}$", message = "학번은 10자리 숫자여야 합니다.")
    private String studentId;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@office\\.kopo\\.ac\\.kr$", message = "한국폴리텍대학교 학교 이메일(@office.kopo.ac.kr)만 사용 가능합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일 인증 코드는 필수입니다.")
    private String verificationCode;
}
