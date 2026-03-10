package com.polgg.polgg.service.portfolio;

import com.polgg.polgg.domain.portfolio.CertificateOwned;
import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.domain.portfolio.Project;
import com.polgg.polgg.domain.user.Member;
import com.polgg.polgg.dto.portfolio.PortfolioRequestDto;
import com.polgg.polgg.dto.portfolio.PortfolioResponseDto;
import com.polgg.polgg.repository.portfolio.PortfolioRepository;
import com.polgg.polgg.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<PortfolioResponseDto> searchPublicPortfolios(String keyword) {
        return portfolioRepository.findAllByIsPublicTrueAndTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword).stream()
                .map(PortfolioResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createPortfolio(String studentId, PortfolioRequestDto dto) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (dto.isMain()) {
            portfolioRepository.findByMemberAndIsMainTrue(member)
                    .ifPresent(p -> p.setMain(false));
        }

        Portfolio portfolio = Portfolio.builder()
                .member(member)
                .title(dto.getTitle())
                .contact(dto.getContact())
                .githubUrl(dto.getGithubUrl())
                .blogUrl(dto.getBlogUrl())
                .introMessage(dto.getIntroMessage())
                .isMain(dto.isMain())
                .isPublic(dto.isPublic())
                .build();

        if (dto.getProjects() != null) {
            dto.getProjects().forEach(pDto -> {
                Project project = Project.builder()
                        .name(pDto.getName())
                        .period(pDto.getPeriod())
                        .videoUrl(pDto.getVideoUrl())
                        .memberCount(pDto.getMemberCount())
                        .role(pDto.getRole())
                        .content(pDto.getContent())
                        .build();
                portfolio.addProject(project);
            });
        }

        if (dto.getCertificates() != null) {
            dto.getCertificates().forEach(cDto -> {
                CertificateOwned certificate = CertificateOwned.builder()
                        .name(cDto.getName())
                        .issueDate(cDto.getIssueDate())
                        .expirationDate(cDto.getExpirationDate())
                        .build();
                portfolio.addCertificate(certificate);
            });
        }

        return portfolioRepository.save(portfolio).getId();
    }

    @Transactional(readOnly = true)
    public List<PortfolioResponseDto> getPortfoliosByMember(String studentId) {
        Member member = memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return portfolioRepository.findAllByMemberOrderByCreatedAtDesc(member).stream()
                .map(PortfolioResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PortfolioResponseDto getPortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));

        if (!portfolio.isPublic()) {
            // 권한 체크 로직 (필요 시 구현)
        }

        return PortfolioResponseDto.from(portfolio);
    }

    @Transactional
    public void updatePortfolio(Long id, String studentId, PortfolioRequestDto dto) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));

        if (!portfolio.getMember().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Member member = portfolio.getMember();
        if (dto.isMain() && !portfolio.isMain()) {
            portfolioRepository.findByMemberAndIsMainTrue(member)
                    .ifPresent(p -> p.setMain(false));
        }

        portfolio.update(
                dto.getTitle(),
                dto.getContact(),
                dto.getGithubUrl(),
                dto.getBlogUrl(),
                dto.getIntroMessage(),
                dto.isMain(),
                dto.isPublic()
        );

        portfolio.getProjects().clear();
        if (dto.getProjects() != null) {
            dto.getProjects().forEach(pDto -> {
                Project project = Project.builder()
                        .name(pDto.getName())
                        .period(pDto.getPeriod())
                        .videoUrl(pDto.getVideoUrl())
                        .memberCount(pDto.getMemberCount())
                        .role(pDto.getRole())
                        .content(pDto.getContent())
                        .build();
                portfolio.addProject(project);
            });
        }

        portfolio.getCertificates().clear();
        if (dto.getCertificates() != null) {
            dto.getCertificates().forEach(cDto -> {
                CertificateOwned certificate = CertificateOwned.builder()
                        .name(cDto.getName())
                        .issueDate(cDto.getIssueDate())
                        .expirationDate(cDto.getExpirationDate())
                        .build();
                portfolio.addCertificate(certificate);
            });
        }
    }

    @Transactional
    public void deletePortfolio(Long id, String studentId) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("포트폴리오를 찾을 수 없습니다."));

        if (!portfolio.getMember().getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        portfolioRepository.delete(portfolio);
    }
}
