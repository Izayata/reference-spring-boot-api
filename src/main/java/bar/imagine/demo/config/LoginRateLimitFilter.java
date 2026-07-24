package bar.imagine.demo.config;

import bar.imagine.demo.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

public class LoginRateLimitFilter extends OncePerRequestFilter {

    public static final String RATE_LIMIT_KEY_PREFIX = "login_rate_limit:";

    private final RedisService redisService;
    private final int maxAttemptsPerHour;

    public LoginRateLimitFilter(RedisService redisService, int maxAttemptsPerHour) {
        this.redisService = redisService;
        this.maxAttemptsPerHour = maxAttemptsPerHour;
    }

    // Increments atomically here, before authentication is attempted, rather than splitting the
    // check (here) from the increment (in the failure handler) — the previous split allowed
    // concurrent requests to all pass the pre-check before any of their increments landed. This
    // counts every POST /login attempt against the limit, not just failed ones, which is the
    // necessary trade-off for the check and increment to be atomic.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if ("/login".equals(request.getServletPath()) && "POST".equals(request.getMethod())) {
            String username = request.getParameter("username");
            if (username != null && !username.isBlank()) {
                long attempts = redisService.atomicIncrementWithTtlOnFirstWrite(
                    RATE_LIMIT_KEY_PREFIX + username, Duration.ofHours(1));
                if (attempts > maxAttemptsPerHour) {
                    response.setStatus(429);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Too many login attempts. Please try again later.\"}");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
