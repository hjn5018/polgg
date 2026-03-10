package com.polgg.polgg.repository.portfolio;

import com.polgg.polgg.domain.portfolio.Portfolio;
import com.polgg.polgg.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByMemberOrderByCreatedAtDesc(Member member);
    Optional<Portfolio> findByMemberAndIsMainTrue(Member member);
}
