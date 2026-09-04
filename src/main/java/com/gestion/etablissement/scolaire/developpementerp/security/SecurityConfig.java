package com.gestion.etablissement.scolaire.developpementerp.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler successHandler;

    /**
     * BCrypt Password Encoder bean — used for encoding and verifying passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * DAO Authentication Provider — links our UserDetailsService + PasswordEncoder.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * AuthenticationManager — needed if we wire it explicitly in controllers.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * HTTP Session Event Publisher — required for concurrent session control.
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * Main Security Filter Chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ── Authorization rules ──────────────────────────────────────
            .authorizeHttpRequests(auth -> auth
                // Resources accessibles sans authentification
                .requestMatchers(
                    "/login", "/login/**",
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    // Swagger UI
                    "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"
                ).permitAll()
                // Defense-in-depth for sensitive user administration
                .requestMatchers("/api-directeur/**", "/api-surveillant/**", "/api-responsable-financier/**", "/api-user/**")
                    .hasRole("DIRECTEUR")
                .requestMatchers("/api-profile/**").authenticated()
                // Tout autre endpoint nécessite une session ou credentials authentifiés
                .anyRequest().authenticated()
            )

            // ── Form Login ────────────────────────────────────────────────
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .httpBasic(Customizer.withDefaults())
            // ── Logout ────────────────────────────────────────────────────
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // ── Session Management ────────────────────────────────────────
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()      // Renew session ID upon login
                .maximumSessions(1)                      // One active session per user
                .maxSessionsPreventsLogin(false)         // Evict old session instead of blocking
            )

            // ── CSRF ──────────────────────────────────────────────────────
            .csrf(AbstractHttpConfigurer::disable)
            // ── Exception Handling (REST JSON 401 & 403) ─────────────────
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"status\": 401, \"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"Accès refusé : privilèges insuffisants.\"}");
                })
            )

            // ── Authentication provider ───────────────────────────────────
            .authenticationProvider(authenticationProvider());


        return http.build();
    }
}
