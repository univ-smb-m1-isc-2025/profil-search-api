package usmb.info803.profile_search.logDelCandidature;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogDelCandidatureService {

    private final logDelCandidatureRepository logDelCandidatureRepository;

    public LogDelCandidatureService(logDelCandidatureRepository logDelCandidatureRepository) {
        this.logDelCandidatureRepository = logDelCandidatureRepository;
    }

    @Transactional
    public LogDelCandidature LogDelCandidature(String emailCandidat, String raison) {
        LogDelCandidature logDelCandidature = new LogDelCandidature(emailCandidat, raison);
        return logDelCandidatureRepository.save(logDelCandidature);
    }

    public List<LogDelCandidature> getLogsByEmailCandidat(String emailCandidat) {
        return logDelCandidatureRepository.findByEmailCandidat(emailCandidat);
    }

    public List<LogDelCandidature> getAllLogs() {
        return logDelCandidatureRepository.findAll();
    }
} 