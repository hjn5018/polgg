package com.polgg.polgg.repository.user;

import com.polgg.polgg.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByStudentId(String studentId);
}
