package usmb.info803.profile_search.tag;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> all() {
        return tagRepository.findAll();
    }

    public Tag tag(long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag tag(String tag) {
        return tagRepository.findByTag(tag).orElse(null);
    }

    public boolean create(String tag) {
        Tag q = tag(tag);
        if (q != null) {
            return false;
        }
        tagRepository.save(new Tag(tag));
        return true;
    }
}
