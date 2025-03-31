package usmb.info803.profile_search.member;

public class CreateMemberBody {

    String nom;
    String prenom;
    String email; 
    Long entrepriseId;

    public CreateMemberBody() {
    }

    public CreateMemberBody(String nom, String prenom, String email, Long entrepriseId) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.entrepriseId = entrepriseId;
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

    public Long getEntrepriseId() {
        return entrepriseId;
    }

    public void setEntrepriseId(Long entrepriseId) {
        this.entrepriseId = entrepriseId;
    }
}
