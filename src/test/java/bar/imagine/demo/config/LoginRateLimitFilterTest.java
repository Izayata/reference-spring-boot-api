package bar.imagine.demo.config;

import bar.imagine.demo.service.RedisService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginRateLimitFilterTest {

    private static final int MAX_ATTEMPTS_PER_HOUR = 10;

    @Mock
    private RedisService redisService;

    @Mock
    private FilterChain filterChain;

    private LoginRateLimitFilter filter() {
        return new LoginRateLimitFilter(redisService, MAX_ATTEMPTS_PER_HOUR);
    }

    private MockHttpServletRequest loginRequest(String username) {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/login");
        request.setServletPath("/login");
        if (username != null) {
            request.setParameter("username", username);
        }
        return request;
    }

    @Test
    void doFilterInternal_shortCircuits429_whenAttemptsOverLimit() throws Exception {
        MockHttpServletRequest request = loginRequest("testuser");
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(redisService.atomicIncrementWithTtlOnFirstWrite(
            LoginRateLimitFilter.RATE_LIMIT_KEY_PREFIX + "testuser", Duration.ofHours(1)))
            .thenReturn((long) MAX_ATTEMPTS_PER_HOUR + 1);

        filter().doFilterInternal(request, response, filterChain);

        assertEquals(429, response.getStatus());
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void doFilterInternal_continuesChain_whenExactlyAtLimit() throws Exception {
        MockHttpServletRequest request = loginRequest("testuser");
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(redisService.atomicIncrementWithTtlOnFirstWrite(
            LoginRateLimitFilter.RATE_LIMIT_KEY_PREFIX + "testuser", Duration.ofHours(1)))
            .thenReturn((long) MAX_ATTEMPTS_PER_HOUR);

        filter().doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_continuesChain_whenBelowLimit() throws Exception {
        MockHttpServletRequest request = loginRequest("testuser");
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(redisService.atomicIncrementWithTtlOnFirstWrite(
            LoginRateLimitFilter.RATE_LIMIT_KEY_PREFIX + "testuser", Duration.ofHours(1)))
            .thenReturn(1L);

        filter().doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_skipsRateLimitCheck_whenNotLoginPath() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/other");
        request.setServletPath("/other");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter().doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(redisService, never()).atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class));
    }

    @Test
    void doFilterInternal_skipsRateLimitCheck_whenUsernameNullOrBlank() throws Exception {
        MockHttpServletRequest request = loginRequest(null);
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter().doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(redisService, never()).atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class));
    }
}
