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
                "/api/bullet-points/{id:[0-9]+}",
                "/api/bullet-points/all",
                "/api/bullet-points/offre/{offreId:[0-9]+}",
                "/api/candidatures/{id:[0-9]+}",
                "/api/candidatures/all",
                "/api/candidatures/create",
                "/api/candidatures/emailCandidat",
                "/api/candidatures/delete/{token:[0-9a-zA-Z]{100}}",
                "/api/invites/verify/{token:[0-9a-zA-Z]{100}}",
                "/api/members/create",
                "/api/members/email/{email:[0-9a-zA-Z@.]+}",
                "/api/offres-questions/{id:[0-9]+}",
                "/api/offres-questions/all",
                "/api/offres/{id:[0-9]+}",
                "/api/offres/all",
                "/api/paragraphes/{id:[0-9]+}",
                "/api/paragraphes/all",
                "/api/questions/{id:[0-9]+}",
                "/api/questions/all"
            );
    }
}
