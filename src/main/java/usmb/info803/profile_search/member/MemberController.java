package usmb.info803.profile_search.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/members")
public class MemberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> members() {
        logger.info("Get all members");
        return memberService.members()
                .stream()
                .toList();
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Member member(@PathVariable("id") long id) {
        logger.info(String.format("Get member by id : %d", id));
        return memberService.memberById(id);
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Member memberByEmail(@PathVariable("email") String email) {
        logger.info(String.format("Get member by email : %s", email));
        return memberService.memberByEmail(email);
    }

    @GetMapping(value = "/nom/{nom}/{prenom}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> memberByNomAndPrenom(@PathVariable("nom") String nom, @PathVariable("prenom") String prenom) {
        logger.info(String.format("Get member by nom : %s and prenom : %s", nom, prenom));
        return memberService.memberByNomAndPrenom(nom, prenom);
    }

    @GetMapping(value = "/actif/{actif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> membersByActif(@PathVariable("actif") boolean actif) {
        logger.info(String.format("Get members by actif : %b", actif));
        return memberService.membersByActif(actif);
    }

    @GetMapping(value = "/entreprise/{entrepriseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Member> membersByEntrepriseId(@PathVariable("entrepriseId") Long entrepriseId) {
        logger.info(String.format("Get members by entreprise id : %d", entrepriseId));
        return memberService.membersByEntrepriseId(entrepriseId);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") long id) {
        logger.info(String.format("Delete member by id : %d", id));
        if (!memberService.delete(id)) {
            logger.error(String.format("User not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User %d not found", id));
        }
        return ResponseEntity.ok(String.format("User %d deleted", id));
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody CreateMemberBody req){
        String nom = req.getNom().replaceAll("[\n\r]", "_");
        String prenom = req.getPrenom().replaceAll("[\n\r]", "_");
        String email = req.getEmail().replaceAll("[\n\r]", "_");
        Long entrepriseId = req.getEntrepriseId();
        logger.info(String.format("Create member with nom : %s, prenom : %s, email : %s, entrepriseId : %d", nom, prenom, email, entrepriseId));
        if(req.getNom() == null || nom.isEmpty()) {
            logger.error("Nom is empty");
            return ResponseEntity.badRequest().body("Nom is empty");
        }
        if(req.getPrenom() == null || prenom.isEmpty()) {
            logger.error("Prenom is empty");
            return ResponseEntity.badRequest().body("Prenom is empty");
        }
        if(req.getEmail() == null || email.isEmpty()) {
            logger.error("Email is empty");
            return ResponseEntity.badRequest().body("Email is empty");
        }
        if(!memberService.create(nom, prenom, email, entrepriseId)) {
            logger.error(String.format("User already exists : %s", email));
            return ResponseEntity.badRequest().body(String.format("User %s already exists", email));
        }
        return ResponseEntity.ok(String.format("User %s created", email));
    }
}
