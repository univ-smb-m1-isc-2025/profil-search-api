package usmb.info803.profile_search.candidature;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usmb.info803.profile_search.logDelCandidature.LogDelCandidatureService;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberService;
import usmb.info803.profile_search.offre.Offre;
import usmb.info803.profile_search.offre.OffreService;
import usmb.info803.profile_search.tag_candidature.TagCandidatureService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {
    
    private final CandidatureService candidatureService;
    private final OffreService offreService;
    private final MemberService memberService;
    private final TagCandidatureService tagCandidatureService;
    private final LogDelCandidatureService logDelCandidatureService;

    public CandidatureController(CandidatureService candidatureService, OffreService offreService, MemberService memberService, TagCandidatureService tagCandidatureService, LogDelCandidatureService logDelCandidatureService) {
        this.candidatureService = candidatureService;
        this.offreService = offreService;
        this.memberService = memberService;
        this.tagCandidatureService = tagCandidatureService;
        this.logDelCandidatureService = logDelCandidatureService;
    }

    @GetMapping("/all")
    public List<CandidatureDTO> getAllCandidatures() {
        return candidatureService.getAllCandidatures()
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }
    
    @GetMapping("/{id}")
    public CandidatureDTO getCandidatureById(@PathVariable("id") long id) {
        Candidature candidature = candidatureService.getCandidatureById(id);
        if (candidature != null) {
            return new CandidatureDTO(candidature, tagCandidatureService);
        } else {
            return null;
        }
    }

    @PostMapping("/emailCandidat")
    public List<CandidatureDTO> getCandidatureByEmailCandidat(@RequestParam("emailCandidat") String emailCandidat) {
        return candidatureService.getCandidatureByEmailCandidat(emailCandidat)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }

    @PostMapping("/name")
    public List<CandidatureDTO> getCandidatureByName(@RequestParam("name") String name) {
        return candidatureService.getCandidatureByName(name)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }

    @PostMapping("/offreId")
    public List<CandidatureDTO> getCandidatureByOffre(@RequestParam("offreID") Long offreId){
        return candidatureService.getCandidatureByOffreId(offreId)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }

    @PostMapping("/assigneeId")
    public List<CandidatureDTO> getCandidatureByAssignee(@RequestParam("assigneeId") Long assigneeId){
        return candidatureService.getCandidatureByAssigneeId(assigneeId)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }
    
    @PostMapping("/closed")
    public List<CandidatureDTO> getCandidatureByClosed(@RequestParam("closed") boolean closed) {
        return candidatureService.getCandidatureByClosed(closed)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }

    @PostMapping("/positif")
    public List<CandidatureDTO> getCandidatureByPositif(@RequestParam("positif") boolean positif) {
        return candidatureService.getCandidatureByPositif(positif)
                .stream()
                .map(candidature -> new CandidatureDTO(candidature, tagCandidatureService))
                .toList();
    }

    @PostMapping("/assign/{candidatureId}/{memberId}")
    public ResponseEntity<?> assignCandidature(@PathVariable("candidatureId") Long candidatureId, @PathVariable("memberId") Long memberId) {
        Candidature candidature = candidatureService.getCandidatureById(candidatureId);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Error: Candidature with ID " + candidatureId + " not found.");
        }
        Member assignee = memberService.memberById(memberId);
        if (assignee == null) {
            return ResponseEntity.badRequest().body("Error: Member with ID " + memberId + " not found.");
        }
        Candidature updatedCandidature = candidatureService.assign(candidatureId, assignee);
        return ResponseEntity.ok().body(new CandidatureDTO(updatedCandidature));
    }

    @PostMapping("/create")
    public ResponseEntity<CandidatureDTO> createCandidature(@RequestBody CandidatureDTO candidatureDTO) {
        Offre offre = offreService.getOffreById(candidatureDTO.getOffreId());
        if (offre == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Candidature candidature = candidatureService.createCandidature(candidatureDTO.getEmailCandidat(), candidatureDTO.getName(), offre);
        return ResponseEntity.ok(new CandidatureDTO(candidature));
    }
    
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<String> deleteCandidature(@PathVariable("token") String token, @RequestBody DeleteCandidatureBody deleteCandidatureBody) {
        Candidature candidature = candidatureService.deleteByDeleteToken(token);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Error: Candidature with token " + token + " not found.");
        }

        logDelCandidatureService.LogDelCandidature(deleteCandidatureBody.getEmailCandidat(), deleteCandidatureBody.getRaison());
        return ResponseEntity.ok("Candidature deleted successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandidatureById(@PathVariable("id") Long id) {
        Candidature candidature = candidatureService.deleteById(id);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Error: Candidature with ID " + id + " not found.");
        }
        return ResponseEntity.ok("Candidature deleted successfully");
    }

    @PostMapping("/updatePositif/{candidatureId}/{positif}")
    public ResponseEntity<?> updatePositif(@PathVariable("candidatureId") Long candidatureId, @PathVariable("positif") boolean positif) {
        Candidature candidature = candidatureService.getCandidatureById(candidatureId);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Error: Candidature with ID " + candidatureId + " not found.");
        }
        Candidature updatedCandidature = candidatureService.updatePositif(candidatureId, positif);
        return ResponseEntity.ok().body(new CandidatureDTO(updatedCandidature, tagCandidatureService));
    }

    @PostMapping("/updateClosed/{candidatureId}/{closed}")
    public ResponseEntity<?> updateClosed(@PathVariable("candidatureId") Long candidatureId, @PathVariable("closed") boolean closed) {
        Candidature candidature = candidatureService.getCandidatureById(candidatureId);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Error: Candidature with ID " + candidatureId + " not found.");
        }
        Candidature updatedCandidature = candidatureService.updateClosed(candidatureId, closed);
        return ResponseEntity.ok().body(new CandidatureDTO(updatedCandidature, tagCandidatureService));
    }
}
