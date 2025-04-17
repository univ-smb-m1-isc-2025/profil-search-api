package usmb.info803.profile_search.logDelCandidature;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import usmb.info803.profile_search.DbEntity;

@Entity
public class LogDelCandidature implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("dateTime")
    private LocalDateTime dateTime;

    @JsonProperty("emailCandidat")
    private String emailCandidat;

    @JsonProperty("raison")
    private String raison;

    @PrePersist
    public void prePersist() {
        dateTime = LocalDateTime.now();
    }

    public LogDelCandidature() {
    }

    public LogDelCandidature(String emailCandidat, String raison) {
        this.emailCandidat = emailCandidat;
        this.raison = raison;
    }

    @Override
    public boolean isValid() {
        return dateTime != null
            && emailCandidat != null
            && !emailCandidat.isEmpty()
            && raison != null
            && !raison.isEmpty();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getEmailCandidat() {
        return emailCandidat;
    }
    public void setEmailCandidat(String emailCandidat) {
        this.emailCandidat = emailCandidat;
    }

    public String getRaison() {
        return raison;
    }
    public void setRaison(String raison) {
        this.raison = raison;
    }
} 