package com.polgg.polgg.domain.portfolio;

import com.polgg.polgg.domain.common.BaseEntity;
import com.polgg.polgg.domain.user.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "portfolios")
public class Portfolio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    private String contact;

    private String githubUrl;

    private String blogUrl;

    @Column(columnDefinition = "TEXT")
    private String introMessage;

    private boolean isMain;

    private boolean isPublic;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CertificateOwned> certificates = new ArrayList<>();

    public void update(String title, String contact, String githubUrl, String blogUrl, String introMessage, boolean isMain, boolean isPublic) {
        this.title = title;
        this.contact = contact;
        this.githubUrl = githubUrl;
        this.blogUrl = blogUrl;
        this.introMessage = introMessage;
        this.isMain = isMain;
        this.isPublic = isPublic;
    }

    public void addProject(Project project) {
        this.projects.add(project);
        project.setPortfolio(this);
    }

    public void addCertificate(CertificateOwned certificate) {
        this.certificates.add(certificate);
        certificate.setPortfolio(this);
    }
}
