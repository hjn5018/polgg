package com.polgg.polgg.dto.portfolio;

import com.polgg.polgg.domain.portfolio.CertificateOwned;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateResponseDto {
    private Long id;
    private String name;
    private String issueDate;
    private String expirationDate;

    public static CertificateResponseDto from(CertificateOwned certificate) {
        return CertificateResponseDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .issueDate(certificate.getIssueDate())
                .expirationDate(certificate.getExpirationDate())
                .build();
    }
}
