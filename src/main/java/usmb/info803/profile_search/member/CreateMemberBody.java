package usmb.info803.profile_search.member;

public class CreateMemberBody {

    String nom;
    String prenom;
    String email; 
    String token;

    public CreateMemberBody() {
    }

    public CreateMemberBody(String nom, String prenom, String email, String token) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.token = token;
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

    public String getToken() {
        return token;
    }
}
