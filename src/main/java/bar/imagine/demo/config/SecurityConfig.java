package bar.imagine.demo.config;

import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService myUserService;
    private final RedisService redisService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${app.login.max-attempts-per-hour:10}")
    private int maxLoginAttemptsPerHour;

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontendUrl));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Not a @Bean: a Filter declared as a Spring bean is auto-registered by FilterRegistrationBean
    // in addition to addFilterBefore below, which would run it twice per request.
    private LoginRateLimitFilter loginRateLimitFilter() {
        return new LoginRateLimitFilter(redisService, maxLoginAttemptsPerHour);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .cors(withDefaults())
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'"))
                .frameOptions(frameOptions -> frameOptions.deny())
                .contentTypeOptions(withDefaults())
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers(
                new AntPathRequestMatcher("/auth-status"),
                new AntPathRequestMatcher("/csrf-token"),
                new AntPathRequestMatcher("/login"),
                // Foods: only GET (and the read-via-POST cart endpoint) are public
                new AntPathRequestMatcher("/v1/foods/**", "GET"),
                new AntPathRequestMatcher("/v1/foods/cart", "POST"),
                new AntPathRequestMatcher("/v1/allergens"),
                new AntPathRequestMatcher("/v1/ingredients"),
                new AntPathRequestMatcher("/v1/password-reset/**"),
                // Only guest order creation is CSRF-exempt; authenticated order creation (POST /v1/orders)
                // and GET /v1/orders/{id} both require CSRF/authentication
                new AntPathRequestMatcher("/v1/orders/guest", "POST"),
                new AntPathRequestMatcher("/actuator/health"),
                // Registration: GETs are exempt by default; isCommonPassword is a pure read (no state change)
                // POST /v1/registration (signup) is intentionally NOT exempt — frontend must send the CSRF token
                new AntPathRequestMatcher("/v1/registration/common-password", "POST")
            ))
            .authorizeHttpRequests(authorize -> authorize
                // Customer enumeration requires ADMIN role
                .requestMatchers(HttpMethod.GET, "/v1/customers").hasRole("ADMIN")
                // Food creation requires authentication; falls through to anyRequest().authenticated()
                .requestMatchers(HttpMethod.POST, "/v1/foods").authenticated()
                // All other food endpoints are public
                .requestMatchers("/v1/foods/**").permitAll()
                // Guest order creation is public; authenticated order creation and order lookup
                // both fall through to anyRequest().authenticated() below
                .requestMatchers(HttpMethod.POST, "/v1/orders/guest").permitAll()
                // Remaining public endpoints
                .requestMatchers(
                    "/auth-status", "/csrf-token", "/login",
                    "/v1/allergens", "/v1/ingredients",
                    "/v1/password-reset/**", "/actuator/health", "/v1/registration/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(loginRateLimitFilter(), UsernamePasswordAuthenticationFilter.class)
            .formLogin(httpForm -> httpForm
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"redirectUrl\": \"" + frontendUrl + "/\"}");
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .failureHandler((request, response, exception) ->
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
            )
            .sessionManagement(sm -> sm.sessionFixation().migrateSession())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Logout successful\"}");
                    response.setStatus(HttpServletResponse.SC_OK);
                })
            )
            .build();
    }
}
