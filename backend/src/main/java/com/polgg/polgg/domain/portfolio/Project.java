package com.polgg.polgg.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(nullable = false)
    private String name;

    private String period;

    private String videoUrl;

    private int memberCount;

    private String role;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(String name, String period, String videoUrl, int memberCount, String role, String content) {
        this.name = name;
        this.period = period;
        this.videoUrl = videoUrl;
        this.memberCount = memberCount;
        this.role = role;
        this.content = content;
    }
}
