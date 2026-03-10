package com.polgg.polgg.domain.user;

import com.polgg.polgg.domain.common.BaseEntity;
import com.polgg.polgg.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @Column(name = "student_id", length = 10, nullable = false)
    private String studentId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder.Default
    private boolean isPortfolioPublic = false;

    public void updateProfile(String name, boolean isPortfolioPublic) {
        this.name = name;
        this.isPortfolioPublic = isPortfolioPublic;
    }
}
