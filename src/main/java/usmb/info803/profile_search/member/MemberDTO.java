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
    private String token;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.nom = member.getNom();
        this.prenom = member.getPrenom();
        this.email = member.getEmail();
        this.actif = member.isActif();
        this.token = member.getToken();
        Entreprise entreprise = member.getEntreprise();
        if(entreprise != null) {
            this.entrepriseName = entreprise.getName();
            this.entrepriseId = entreprise.getId();
        } else {
            this.entrepriseName = null;
            this.entrepriseId = null;
        }
    }

    public static MemberDTO fromMember(Member member) {
        return new MemberDTO(member);
    }

    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public boolean isActif() {
        return actif;
    }
    public String getEntrepriseName() {
        return entrepriseName;
    }
    public Long getEntrepriseId() {
        return entrepriseId;
    }
    public String getToken() {
        return token;
    }
}
