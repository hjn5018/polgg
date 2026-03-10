package com.polgg.polgg.service.user;

import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.user.ProfileResponseDto;
import com.polgg.polgg.dto.user.ProfileUpdateRequestDto;
import com.polgg.polgg.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(String studentId) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return ProfileResponseDto.builder()
                .studentId(member.getStudentId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .isPortfolioPublic(member.isPortfolioPublic())
                .build();
    }

    @Transactional
    public void updateProfile(String studentId, ProfileUpdateRequestDto request) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        member.updateProfile(request.getName(), request.isPortfolioPublic());
    }
}
