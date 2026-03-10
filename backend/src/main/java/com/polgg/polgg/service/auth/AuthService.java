package com.polgg.polgg.service.auth;

import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.auth.LoginRequestDto;
import com.polgg.polgg.dto.auth.LoginResponseDto;
import com.polgg.polgg.dto.auth.SignupRequestDto;
import com.polgg.polgg.enums.UserRole;
import com.polgg.polgg.repository.user.MemberRepository;
import com.polgg.polgg.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(SignupRequestDto request) {
        // 1. 이메일 인증 코드 재검증
        if (!emailService.verifyCode(request.getEmail(), request.getVerificationCode())) {
            throw new IllegalArgumentException("이메일 인증 코드가 올바르지 않거나 만료되었습니다.");
        }

        // 2. 학번 및 이메일 중복 체크
        if (memberRepository.existsByStudentId(request.getStudentId())) {
            throw new IllegalArgumentException("이미 가입된 학번입니다.");
        }
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 3. 회원 저장
        Member member = Member.builder()
                .studentId(request.getStudentId())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(UserRole.USER)
                .build();

        memberRepository.save(member);
        log.info("New member registered: {} ({})", member.getName(), member.getStudentId());
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        // 이메일 또는 학번으로 사용자 조회
        Member member = memberRepository.findByEmail(request.getLoginId())
                .orElseGet(() -> memberRepository.findByStudentId(request.getLoginId())
                        .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 또는 학번입니다.")));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(member.getStudentId(), member.getEmail(), member.getRole().name());

        return LoginResponseDto.builder()
                .token(token)
                .studentId(member.getStudentId())
                .name(member.getName())
                .role(member.getRole().name())
                .build();
    }
}
