package com.polgg.polgg.domain.application;

import com.polgg.polgg.domain.common.BaseEntity;
import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.domain.user.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "application_histories")
public class ApplicationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio; // 지원 시 제출한 포트폴리오 (Optional)

    @Column(nullable = false)
    private String companyName;

    private String channel; // 사람인, 원티드 등

    private String category; // 서류, 기술면접, 최종 등

    private String status; // 합격, 불합격, 진행중

    private LocalDateTime appliedAt;

    public void update(String companyName, String channel, String category, String status, LocalDateTime appliedAt, Portfolio portfolio) {
        this.companyName = companyName;
        this.channel = channel;
        this.category = category;
        this.status = status;
        this.appliedAt = appliedAt;
        this.portfolio = portfolio;
    }
}
