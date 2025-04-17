package usmb.info803.profile_search.tag_candidature;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usmb.info803.profile_search.candidature.Candidature;
import usmb.info803.profile_search.candidature.CandidatureService;
import usmb.info803.profile_search.tag.Tag;
import usmb.info803.profile_search.tag.TagService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/tag-candidatures")
public class TagCandidatureController {

    private final TagCandidatureService tagCandidatureService;
    private final TagService tagService;
    private final CandidatureService candidatureService;

    public TagCandidatureController(TagCandidatureService tagCandidatureService, TagService tagService, CandidatureService candidatureService) {
        this.tagCandidatureService = tagCandidatureService;
        this.tagService = tagService;
        this.candidatureService = candidatureService;
    }

    @GetMapping("add/{tagId}/{candidatureId}")
    public ResponseEntity<String> addTagToCandidature(@PathVariable("tagId") Long tagId, @PathVariable("candidatureId") Long candidatureId) {
        Tag tag = tagService.tag(tagId);
        if (tag == null) {
            return ResponseEntity.badRequest().body("Tag not found");
        }
        Candidature candidature = candidatureService.getCandidatureById(candidatureId);
        if (candidature == null) {
            return ResponseEntity.badRequest().body("Candidature not found");
        }
        
        TagCandidature tagCandidature = tagCandidatureService.save(new TagCandidature(tag, candidature));
        if (tagCandidature != null) {
            return ResponseEntity.ok(String.format("Tag %s added to Candidature %s", tag.getTag(), candidature.getId()));
        } else {
            return ResponseEntity.badRequest().body("TagCandidature already exists");
        }
    }
    
    @DeleteMapping("/delete/{tagId}/{candidatureId}")
    public ResponseEntity<String> deleteTagFromCandidature(@PathVariable("tagId") Long tagId, @PathVariable("candidatureId") Long candidatureId) {
        TagCandidature tagCandidature = tagCandidatureService.findByTagIdAndCandidatureId(tagId, candidatureId);
        if (tagCandidature != null) {
            tagCandidatureService.delete(tagCandidature);
            return ResponseEntity.ok("Tag removed from Candidature");
        } else {
            return ResponseEntity.badRequest().body("TagCandidature not found");
        }
    }

    @DeleteMapping("/deleteAll/{candidatureId}")
    public ResponseEntity<String> deleteAllTagsFromCandidature(@PathVariable("candidatureId") Long candidatureId) {
        tagCandidatureService.deleteByCandidatureId(candidatureId);
        return ResponseEntity.ok("All tags removed from Candidature");
    }
    
}
