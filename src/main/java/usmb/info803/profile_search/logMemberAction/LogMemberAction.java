package usmb.info803.profile_search.logMemberAction;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import usmb.info803.profile_search.DbEntity;
import usmb.info803.profile_search.member.Member;

@Entity
public class LogMemberAction implements DbEntity {

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @JsonProperty("dateTime")
    private LocalDateTime dateTime;

    @JsonProperty("member")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @JsonProperty("message")
    private String message;

    public LogMemberAction() {
    }

    public LogMemberAction(LocalDateTime dateTime, Member member, String message) {
        this.dateTime = dateTime;
        this.member = member;
        this.message = message;
    }

    @Override
    public boolean isValid() {
        return dateTime != null 
            && message != null 
            && !message.isEmpty();
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

    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
} 