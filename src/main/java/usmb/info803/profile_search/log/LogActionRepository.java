package usmb.info803.profile_search.log;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import usmb.info803.profile_search.member.Member;

public interface LogActionRepository extends JpaRepository<LogAction, Long> {
    List<LogAction> findByMember(Member member);
    List<LogAction> findByMemberId(Long memberId);
} 