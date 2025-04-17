package usmb.info803.profile_search.logMemberAction;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.member.Member;

@Service
public class LogMemberActionService {

    private final LogMemberActionRepository logActionRepository;

    public LogMemberActionService(LogMemberActionRepository logActionRepository) {
        this.logActionRepository = logActionRepository;
    }

    @Transactional
    public LogMemberAction logAction(Member member, String message) {
        LogMemberAction logAction = new LogMemberAction(LocalDateTime.now(), member, message);
        return logActionRepository.save(logAction);
    }

    public List<LogMemberAction> getLogsByMember(Member member) {
        return logActionRepository.findByMember(member);
    }

    public List<LogMemberAction> getLogsByMemberId(Long memberId) {
        return logActionRepository.findByMemberId(memberId);
    }

    public List<LogMemberAction> getAllLogs() {
        return logActionRepository.findAll();
    }
} 