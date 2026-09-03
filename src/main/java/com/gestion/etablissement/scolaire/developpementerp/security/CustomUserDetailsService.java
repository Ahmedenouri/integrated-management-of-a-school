package com.gestion.etablissement.scolaire.developpementerp.security;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Utilisateur;
import com.gestion.etablissement.scolaire.developpementerp.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Loading user by email: {}", email);

        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found with email: {}", email);
                    return new UsernameNotFoundException("Utilisateur introuvable avec l'email : " + email);
                });

        boolean estActif = Boolean.TRUE.equals(utilisateur.getEstActif());

        if (!estActif) {
            log.warn("User account is disabled for email: {}", email);
            throw new UsernameNotFoundException("Compte désactivé pour l'utilisateur : " + email);
        }

        String roleName = utilisateur.getRole().name();
        String roleWithPrefix = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        log.debug("User {} authenticated with role {}", email, roleWithPrefix);

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .authorities(List.of(new SimpleGrantedAuthority(roleWithPrefix)))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!estActif)
                .build();
    }
}