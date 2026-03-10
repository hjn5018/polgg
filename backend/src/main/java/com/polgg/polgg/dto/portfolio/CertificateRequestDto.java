package com.polgg.polgg.dto.portfolio;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateRequestDto {
    private String name;
    private String issueDate;
    private String expirationDate;
}
