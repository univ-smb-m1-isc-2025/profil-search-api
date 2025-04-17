package usmb.info803.profile_search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ValidMemberTokenInterceptor requestInterceptor;

    public WebConfig(ValidMemberTokenInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(
                "/api/bullet-points/{id}",
                "/api/bullet-points/all",
                "/api/bullet-points/offre/{offreId}",
                "/api/candidatures/{id}",
                "/api/candidatures/all",
                "/api/candidatures/emailCandidat",
                "/api/invites/verify/{token}",
                "/api/members/email/{email}",
                "/api/offres-questions/{id}",
                "/api/offres-questions/all",
                "/api/offres/{id}",
                "/api/offres/all",
                "/api/paragraphes/{id}",
                "/api/paragraphes/all",
                "/api/questions/{id}",
                "/api/questions/all"
            );
    }
}
