package com.polgg.polgg.service.application;

import com.polgg.polgg.domain.application.ApplicationHistory;
import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.application.ApplicationRequestDto;
import com.polgg.polgg.dto.application.ApplicationResponseDto;
import com.polgg.polgg.repository.application.ApplicationHistoryRepository;
import com.polgg.polgg.repository.portfolio.PortfolioRepository;
import com.polgg.polgg.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationHistoryRepository applicationHistoryRepository;
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Long create(String studentId, ApplicationRequestDto dto) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Portfolio portfolio = null;
        if (dto.getPortfolioId() != null) {
            portfolio = portfolioRepository.findById(dto.getPortfolioId())
                    .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));
        }

        ApplicationHistory applicationHistory = ApplicationHistory.builder()
                .member(member)
                .portfolio(portfolio)
                .companyName(dto.getCompanyName())
                .channel(dto.getChannel())
                .category(dto.getCategory())
                .status(dto.getStatus())
                .appliedAt(dto.getAppliedAt())
                .build();

        return applicationHistoryRepository.save(applicationHistory).getId();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponseDto> getMyApplications(String studentId) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return applicationHistoryRepository.findAllByMemberOrderByAppliedAtDesc(member).stream()
                .map(ApplicationResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, String studentId, ApplicationRequestDto dto) {
        ApplicationHistory applicationHistory = applicationHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("지원 기록을 찾을 수 없습니다."));

        if (!applicationHistory.getMember().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Portfolio portfolio = null;
        if (dto.getPortfolioId() != null) {
            portfolio = portfolioRepository.findById(dto.getPortfolioId())
                    .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));
        }

        applicationHistory.update(
                dto.getCompanyName(),
                dto.getChannel(),
                dto.getCategory(),
                dto.getStatus(),
                dto.getAppliedAt(),
                portfolio
        );
    }

    @Transactional
    public void delete(Long id, String studentId) {
        ApplicationHistory applicationHistory = applicationHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("지원 기록을 찾을 수 없습니다."));

        if (!applicationHistory.getMember().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        applicationHistoryRepository.delete(applicationHistory);
    }
}
