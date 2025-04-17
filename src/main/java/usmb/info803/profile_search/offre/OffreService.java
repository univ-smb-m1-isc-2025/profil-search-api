package usmb.info803.profile_search.offre;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberService;

@Service
public class OffreService {

    private final OffreRepository offreRepository;
    private final MemberService memberService;

    public OffreService(OffreRepository offreRepository, MemberService memberService) {
        this.offreRepository = offreRepository;
        this.memberService = memberService;
    }

    @Transactional(readOnly = true)
    public List<Offre> getAllOffres() {
        List<Offre> offres = offreRepository.findAll();
        // Force l'initialisation des collections pour éviter les problèmes de LazyInitializationException
        for (Offre offre : offres) {
            // Initialiser les collections
            offre.getParagraphes().size();
            offre.getBulletPoints().size();
            offre.getQuestions().size();
        }
        return offres;
    }

    @Transactional(readOnly = true)
    public Offre getOffreById(Long id) {
        Offre offre = offreRepository.findById(id).orElse(null);
        if (offre != null) {
            // Initialiser les collections
            offre.getParagraphes().size();
            offre.getBulletPoints().size();
            offre.getQuestions().size();
        }
        return offre;
    }

    public Offre createOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    @Transactional
    public Offre createOffreFromDTO(OffreCreationDTO dto) {
        Member member = memberService.memberById(dto.getMemberId());
        if (member == null) {
            return null; // Membre non trouvé
        }

        Offre offre = new Offre(dto.getTitre(), member, dto.isEst_publiee());
        return offreRepository.save(offre);
    }

    public boolean deleteOffre(Long id) {
        if (offreRepository.existsById(id)) {
            offreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
