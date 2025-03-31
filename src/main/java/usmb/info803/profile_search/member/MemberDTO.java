package usmb.info803.profile_search.member;

import usmb.info803.profile_search.entreprise.Entreprise;

public class MemberDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private boolean actif;
    private String entrepriseName;
    private Long entrepriseId;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.nom = member.getNom();
        this.prenom = member.getPrenom();
        this.email = member.getEmail();
        this.actif = member.isActif();
        Entreprise entreprise = member.getEntreprise();
        if(entreprise != null) {
            this.entrepriseName = entreprise.getName();
            this.entrepriseId = entreprise.getId();
        } else {
            this.entrepriseName = null;
            this.entrepriseId = null;
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActif() {
        return actif;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public String getEntrepriseName() {
        return entrepriseName;
    }
    public void setEntrepriseName(String entrepriseName) {
        this.entrepriseName = entrepriseName;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }
    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
}
