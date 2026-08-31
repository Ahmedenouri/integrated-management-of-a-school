package com.gestion.etablissement.scolaire.developpementerp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String redirectUrl = determineTargetUrl(authorities);

        log.info("User '{}' logged in successfully. Redirecting to: {}", authentication.getName(), redirectUrl);

        if (response.isCommitted()) {
            log.warn("Response already committed. Cannot redirect to {}", redirectUrl);
            return;
        }

        response.sendRedirect(redirectUrl);
    }

    private String determineTargetUrl(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            return switch (role) {
                case "ROLE_DIRECTEUR" -> "/api-dashboard/stats";
                case "ROLE_RESPONSABLE_FINANCIER" -> "/api-paiement/getAllPaiements";
                case "ROLE_SURVEILLANT" -> "/api-absence/getAllAbsences";
                case "ROLE_PROFESSEUR" -> "/api-note/getAllNotes";
                case "ROLE_ETUDIANT" -> "/api-note/getAllNotes";
                default -> "/login?error=role_inconnu";
            };
        }
        return "/login?error=no_role";
    }
}
