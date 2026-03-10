package com.polgg.polgg.controller.auth;

import com.polgg.polgg.dto.auth.EmailRequestDto;
import com.polgg.polgg.dto.auth.LoginRequestDto;
import com.polgg.polgg.dto.auth.LoginResponseDto;
import com.polgg.polgg.dto.auth.SignupRequestDto;
import com.polgg.polgg.service.auth.AuthService;
import com.polgg.polgg.service.auth.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmailService emailService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto request) {
        log.info("Signup request for student ID: {}", request.getStudentId());
        try {
            authService.signup(request);
            return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("Login request for ID: {}", request.getLoginId());
        try {
            LoginResponseDto response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/email-verify")
    public ResponseEntity<?> sendEmailVerifyCode(@Valid @RequestBody EmailRequestDto request) {
        log.info("Request for email verification: {}", request.getEmail());
        emailService.sendVerificationEmail(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "인증 코드가 이메일(@office.kopo.ac.kr)로 전송되었습니다."));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        if (emailService.verifyCode(email, code)) {
            return ResponseEntity.ok(Map.of("message", "이메일 인증이 성공하였습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "인증 코드가 올바르지 않거나 만료되었습니다."));
        }
    }
}
