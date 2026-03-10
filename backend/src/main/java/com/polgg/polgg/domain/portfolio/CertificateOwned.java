package com.polgg.polgg.domain.portfolio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "certificates_owned")
public class CertificateOwned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(nullable = false)
    private String name;

    private String issueDate;

    private String expirationDate;

    public void update(String name, String issueDate, String expirationDate) {
        this.name = name;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }
}
