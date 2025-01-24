package usmb.info803.profile_search;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileSearchController {

    @GetMapping("/search")
    public String search() {
        return "Hello from Profile Search!";
    }
}