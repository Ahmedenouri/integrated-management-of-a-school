package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ChangePasswordRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.UpdateProfileRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfileResponse;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Professeur;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Utilisateur;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.BusinessException;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.repositories.UtilisateurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IProfileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ProfileServiceImpl implements IProfileService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String email) {
        log.debug("Récupération du profil pour l'utilisateur : {}", email);

        Utilisateur utilisateur = findUserOrThrow(email);

        ProfileResponse.ProfileResponseBuilder builder = ProfileResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .telephone(utilisateur.getTelephone())
                .role(utilisateur.getRole())
                .estActif(utilisateur.getEstActif())
                .dateCreation(utilisateur.getDateCreation());

        if (utilisateur instanceof Etudiant etudiant) {
            builder.cne(etudiant.getCne())
                    .dateNaissance(etudiant.getDateNaissance())
                    .nomParent(etudiant.getNomParent())
                    .telephoneParent(etudiant.getTelephoneParent())
                    .emailParent(etudiant.getEmailParent());

            if (etudiant.getClasse() != null) {
                builder.classeId(etudiant.getClasse().getId())
                        .classeNom(etudiant.getClasse().getNom());
            }
        } else if (utilisateur instanceof Professeur professeur) {
            builder.specialite(professeur.getSpecialite());
        }

        return builder.build();
    }

    @Override
    public ProfileResponse updateProfile(String email, UpdateProfileRequest request) {
        log.debug("Mise à jour des informations de profil pour : {}", email);

        Utilisateur utilisateur = findUserOrThrow(email);

        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        if (request.getTelephone() != null) {
            utilisateur.setTelephone(request.getTelephone());
        }

        utilisateurRepository.save(utilisateur);
        return getProfile(email);
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        log.debug("Tentative de changement de mot de passe pour : {}", email);

        Utilisateur utilisateur = findUserOrThrow(email);

        if (!passwordEncoder.matches(request.getAncienMotDePasse(), utilisateur.getMotDePasse())) {
            log.warn("Échec changement mot de passe pour {} : ancien mot de passe invalide", email);
            throw new BusinessException("L'ancien mot de passe fourni est incorrect.");
        }

        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new BusinessException("Le nouveau mot de passe et sa confirmation ne correspondent pas.");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);

        log.info("Mot de passe mis à jour avec succès pour l'utilisateur : {}", email);
    }

    private Utilisateur findUserOrThrow(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable avec l'email : " + email));
    }
}
