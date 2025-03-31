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

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public Long getEntrepriseId() {
        return entrepriseId;
    }
}
