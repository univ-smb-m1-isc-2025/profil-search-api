package usmb.info803.profile_search.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usmb.info803.profile_search.entreprise.Entreprise;
import usmb.info803.profile_search.invitation.InvitationService;

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
    private final InvitationService invitationService;

    public MemberController(MemberService memberService, InvitationService invitationService) {
        this.memberService = memberService;
        this.invitationService = invitationService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDTO> members() {
        logger.info("Get all members");
        return memberService.members()
                .stream()
                .map(MemberDTO::new)
                .toList();
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MemberDTO member(@PathVariable("id") long id) {
        logger.info(String.format("Get member by id : %d", id));
        return new MemberDTO(memberService.memberById(id));
    }

    @GetMapping(value = "/actif/{actif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDTO> membersByActif(@PathVariable("actif") boolean actif) {
        logger.info(String.format("Get members by actif : %b", actif));
        return memberService.membersByActif(actif)
                .stream()
                .map(MemberDTO::new)
                .toList();
    }

    @GetMapping(value = "/entreprise/{entrepriseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberDTO> membersByEntrepriseId(@PathVariable("entrepriseId") Long entrepriseId) {
        logger.info(String.format("Get members by entreprise id : %d", entrepriseId));
        return memberService.membersByEntrepriseId(entrepriseId)
                .stream()
                .map(MemberDTO::new)
                .toList();
    }

    @PostMapping(value = "/deactivate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deactivate(@PathVariable("id") long id) {
        logger.info(String.format("Deactivate member by id : %d", id));
        Member member = memberService.memberById(id);
        if (member == null) {
            logger.error(String.format("User not found : %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User %d not found", id));
        }
        member.setActif(false);
        if(!memberService.save(member)){
            logger.error("Error while saving member");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error while saving member");
        }
        return ResponseEntity.ok(String.format("User %d deactivated", id));
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
        String regex = "[\n\r]";
        String nom = req.getNom().replaceAll(regex, "_");
        String prenom = req.getPrenom().replaceAll(regex, "_");
        String email = req.getEmail().replaceAll(regex, "_");
        String token = req.getToken();
        logger.info(String.format("Create member with nom : %s, prenom : %s, email : %s, entrepriseId : %s", nom, prenom, email, token));
        
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
        if(req.getToken() == null || token.isEmpty()) {
            logger.error("Token is empty");
            return ResponseEntity.badRequest().body("Token is empty");
        }
        Entreprise entreprise = invitationService.verify(token);
        if(entreprise == null) {
            logger.error("Token is invalid");
            return ResponseEntity.badRequest().body("Token is invalid");
        }

        String createError = memberService.create(nom, prenom, email, entreprise);
        if(!createError.equals("")) {
            logger.error(String.format("User creation error : %s", createError));
            return ResponseEntity.badRequest().body(String.format("User creation error : %s", createError));
        }
        
        return ResponseEntity.ok(String.format("User %s created", email));
    }
}
