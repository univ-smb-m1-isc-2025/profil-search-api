package usmb.info803.profile_search.offre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import usmb.info803.profile_search.bullet_point.BulletPoint;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.offres_question.OffreQuestionDTO;
import usmb.info803.profile_search.paragraphe.Paragraphe;

public class OffreDTO {

    private Long id;

    private String titre;

    @JsonProperty("user_source")
    private Member userSource;

    @JsonProperty("est_publiee")
    private boolean estPubliee;

    private List<Paragraphe> paragraphes = new ArrayList<>();

    private List<BulletPoint> bulletPoints = new ArrayList<>();

    private List<OffreQuestionDTO> questions = new ArrayList<>();

    public OffreDTO() {
    }

    public static OffreDTO fromEntity(Offre offre) {
        OffreDTO dto = new OffreDTO();
        dto.setId(offre.getId());
        dto.setTitre(offre.getTitre());
        dto.setUserSource(offre.getuserSource());
        dto.setEstPubliee(offre.isEstPubliee());

        // Convertir paragraphes
        if (offre.getParagraphes() != null) {
            dto.setParagraphes(new ArrayList<>(offre.getParagraphes()));
        }

        // Convertir bulletPoints
        if (offre.getBulletPoints() != null) {
            dto.setBulletPoints(new ArrayList<>(offre.getBulletPoints()));
        }

        // Convertir questions en DTO avec tous les détails
        if (offre.getQuestions() != null) {
            dto.setQuestions(
                    offre.getQuestions().stream()
                            .map(oq -> OffreQuestionDTO.fromQuestion(oq.getQuestion()))
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Member getUserSource() {
        return userSource;
    }

    public void setUserSource(Member userSource) {
        this.userSource = userSource;
    }

    public boolean isEstPubliee() {
        return estPubliee;
    }

    public void setEstPubliee(boolean estPubliee) {
        this.estPubliee = estPubliee;
    }

    public List<Paragraphe> getParagraphes() {
        return paragraphes;
    }

    public void setParagraphes(List<Paragraphe> paragraphes) {
        this.paragraphes = paragraphes;
    }

    public List<BulletPoint> getBulletPoints() {
        return bulletPoints;
    }

    public void setBulletPoints(List<BulletPoint> bulletPoints) {
        this.bulletPoints = bulletPoints;
    }

    public List<OffreQuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<OffreQuestionDTO> questions) {
        this.questions = questions;
    }
}
