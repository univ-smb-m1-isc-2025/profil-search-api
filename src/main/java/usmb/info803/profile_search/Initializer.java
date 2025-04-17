package usmb.info803.profile_search;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.entreprise.EntrepriseRepository;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberRepository;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreRepository;

@Service
public class Initializer {

    private final EntrepriseRepository entrepriseRepository;
    private final MemberRepository memberRepository;
    private final OffreRepository offreRepository;

    public Initializer(
            EntrepriseRepository entrepriseRepository,
            MemberRepository memberRepository,
            OffreRepository offreRepository
    ) {
        this.entrepriseRepository = entrepriseRepository;
        this.memberRepository = memberRepository;
        this.offreRepository = offreRepository;
    }

    @PostConstruct
    public void init() {
        // Nettoyer les données existantes
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

        // Créer des offres
        Offre offre1 = new Offre("Développeur Java", member1, true);
        Offre offre2 = new Offre("Ingénieur Machine Learning", member2, false);

        offre1 = offreRepository.save(offre1);
        offre2 = offreRepository.save(offre2);
        System.out.println("Offres créées: " + offre1.getTitre() + ", " + offre2.getTitre());

    }
}
