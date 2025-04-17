package usmb.info803.profile_search.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usmb.info803.profile_search.member.Member;
import usmb.info803.profile_search.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ValidMemberTokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MemberService memberService;

    public ValidMemberTokenInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
        
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            logger.error("Missing or empty Authorization header");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or empty Authorization header");
            return false;
        }

        if (!token.startsWith("Bearer ")) {
            logger.error("Invalid Authorization header format : {}", token);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header format : must start with 'Bearer-'");
            return false;
        }
        
        Member member = memberService.memberByToken(token.substring(7));
        if (member == null) {
            logger.error("Invalid token: {}", token);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }
        logger.info("Valid token for member: {}", member.getId());

        return true;
    }
}
