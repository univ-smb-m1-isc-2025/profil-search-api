package usmb.info803.profile_search.log;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.member.Member;

@Service
public class LogActionService {

    private final LogActionRepository logActionRepository;

    public LogActionService(LogActionRepository logActionRepository) {
        this.logActionRepository = logActionRepository;
    }

    @Transactional
    public LogAction logAction(Member member, String message) {
        LogAction logAction = new LogAction(LocalDateTime.now(), member, message);
        return logActionRepository.save(logAction);
    }

    public List<LogAction> getLogsByMember(Member member) {
        return logActionRepository.findByMember(member);
    }

    public List<LogAction> getLogsByMemberId(Long memberId) {
        return logActionRepository.findByMemberId(memberId);
    }

    public List<LogAction> getAllLogs() {
        return logActionRepository.findAll();
    }
} 