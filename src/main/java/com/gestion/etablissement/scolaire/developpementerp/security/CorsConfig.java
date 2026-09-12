package com.gestion.etablissement.scolaire.developpementerp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Global CORS configuration for the application.
 *
 * <p>Registers a {@link CorsConfigurationSource} bean that is automatically picked up
 * by Spring Security's {@code .cors(Customizer.withDefaults())} in
 * {@link SecurityConfig#securityFilterChain}.  This allows the React frontend
 * (Vite on 5173 or CRA on 3000) to communicate with the Spring Boot API on 8080.</p>
 *
 * <p><strong>Preflight handling:</strong> Spring Security is instructed to let OPTIONS
 * preflight requests through before any authentication filter runs, so the browser
 * always receives a valid CORS response.</p>
 *
 * <p><strong>Configuration:</strong> Allows origins http://localhost:5173 and http://localhost:3000,
 * methods GET, POST, PUT, DELETE, OPTIONS, headers Authorization and Content-Type,
 * exposes Authorization header, and sets allowCredentials(true).</p>
 */
@Configuration
public class CorsConfig {

    /**
     * Defines the CORS policy applied to every API endpoint ({@code /**}).
     *
     * <ul>
     *   <li><b>Allowed origins</b> – React dev servers on ports 5173 (Vite) and 3000 (CRA).</li>
     *   <li><b>Allowed methods</b> – GET, POST, PUT, DELETE, OPTIONS.</li>
     *   <li><b>Allowed headers</b> – Authorization, Content-Type.</li>
     *   <li><b>Exposed headers</b> – Authorization (so the React client can read JWT tokens
     *       returned in response headers).</li>
     *   <li><b>Credentials</b> – {@code true} to support JWT cookies / session cookies.</li>
     *   <li><b>Max age</b> – 3600 s (1 h) to cache preflight results in the browser.</li>
     * </ul>
     *
     * @return a {@link CorsConfigurationSource} registered for all URL patterns.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ── Allowed origins ───────────────────────────────────────────────
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",   // Vite dev server
                "http://localhost:3000"    // CRA / alternative dev server
        ));

        // ── Allowed HTTP methods ──────────────────────────────────────────
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // ── Allowed request headers ───────────────────────────────────────
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        // ── Exposed response headers (readable by the browser JS) ─────────
        // Exposes the Authorization header so the React client can extract
        // JWT tokens returned in HTTP response headers.
        config.setExposedHeaders(List.of("Authorization"));

        // ── Credentials (cookies / Authorization header with JWT) ─────────
        // Must be true when the frontend sends credentials (withCredentials: true).
        // NOTE: setAllowedOrigins must NOT use "*" when this is true.
        config.setAllowCredentials(true);

        // ── Preflight cache duration ──────────────────────────────────────
        // Browser caches the preflight response for 1 hour to reduce OPTIONS requests.
        config.setMaxAge(3600L);

        // Apply this configuration to every URL in the application
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
