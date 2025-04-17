package usmb.info803.profile_search.logMemberAction;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import usmb.info803.profile_search.member.Member;

public interface LogMemberActionRepository extends JpaRepository<LogMemberAction, Long> {
    List<LogMemberAction> findByMember(Member member);
    List<LogMemberAction> findByMemberId(Long memberId);
} 