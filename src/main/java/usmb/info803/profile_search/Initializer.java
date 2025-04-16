package usmb.info803.profile_search;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseRepository;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberRepository;
import usmb.info803.profile_search.log.LogAction;
import usmb.info803.profile_search.log.LogActionRepository;

@Service
public class Initializer {
    
    private final EntrepriseRepository entrepriseRepository;
    private final MemberRepository memberRepository;
    private final LogActionRepository logActionRepository;

    public Initializer(
        EntrepriseRepository entrepriseRepository, 
        MemberRepository memberRepository,
        LogActionRepository logActionRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.memberRepository = memberRepository;
        this.logActionRepository = logActionRepository;
    }

    @PostConstruct
    public void init() {
        // Nettoyer les données existantes
        logActionRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        entrepriseRepository.deleteAllInBatch();

        System.out.println("=== INITIALISATION DES DONNÉES ===");
        
        // Créer des entreprises
        Entreprise usmb = new Entreprise("USMB");
        Entreprise google = new Entreprise("Google");
        Entreprise apple = new Entreprise("Apple");
        
        usmb = entrepriseRepository.save(usmb);
        google = entrepriseRepository.save(google);
        apple = entrepriseRepository.save(apple);
        System.out.println("Entreprises créées: USMB, Google, Apple");

        // Créer des membres
        Member member1 = new Member("Dupont", "Jean", "jean.dupont@usmb.fr", usmb);
        Member member2 = new Member("Martin", "Sophie", "sophie.martin@google.com", google);
        Member member3 = new Member("Dubois", "Pierre", "pierre.dubois@apple.com", apple);
        
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        System.out.println("Membres créés: " + member1.getPrenom() + ", " + member2.getPrenom() + ", " + member3.getPrenom());

        // Créer des logs d'actions
        LogAction log1 = createLogAction(member1, "Inscription réussie");
        LogAction log2 = createLogAction(member1, "Connexion");
        LogAction log3 = createLogAction(member2, "Inscription réussie");
        LogAction log4 = createLogAction(member2, "Mise à jour du profil");
        LogAction log5 = createLogAction(member3, "Inscription réussie");
        System.out.println("Logs créés: " + log1.getId() + ", " + log2.getId() + ", " + log3.getId() + ", " + log4.getId() + ", " + log5.getId());
        
        // Validation des requêtes de recherche
        List<LogAction> member1Logs = logActionRepository.findByMember(member1);
        System.out.println("Logs pour " + member1.getPrenom() + ": " + member1Logs.size() + " entrées");
        for (LogAction log : member1Logs) {
            System.out.println(" - " + log.getDateTime() + ": " + log.getMessage());
        }
        
        List<LogAction> member2Logs = logActionRepository.findByMemberId(member2.getId());
        System.out.println("Logs pour " + member2.getPrenom() + " (par ID): " + member2Logs.size() + " entrées");
        for (LogAction log : member2Logs) {
            System.out.println(" - " + log.getDateTime() + ": " + log.getMessage());
        }
        
        List<LogAction> allLogs = logActionRepository.findAll();
        System.out.println("Total des logs: " + allLogs.size() + " entrées");
        
        System.out.println("=== INITIALISATION TERMINÉE ===");
    }
    
    private LogAction createLogAction(Member member, String message) {
        LogAction logAction = new LogAction(LocalDateTime.now(), member, message);
        return logActionRepository.save(logAction);
    }
}
