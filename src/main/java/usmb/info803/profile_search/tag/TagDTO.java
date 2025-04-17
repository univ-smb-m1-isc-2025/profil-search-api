package usmb.info803.profile_search.tag;

public class TagDTO {
    
    private Long id;
    private String tag;

    public TagDTO() {
    }

    public TagDTO(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
    }

    public Long getId() {
        return id;
    }
    public String getTag() {
        return tag;
    }
}
