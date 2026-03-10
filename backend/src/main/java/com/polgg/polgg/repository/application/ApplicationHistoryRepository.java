package com.polgg.polgg.repository.application;

import com.polgg.polgg.domain.application.ApplicationHistory;
import com.polgg.polgg.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory, Long> {
    List<ApplicationHistory> findAllByMemberOrderByAppliedAtDesc(Member member);
}
