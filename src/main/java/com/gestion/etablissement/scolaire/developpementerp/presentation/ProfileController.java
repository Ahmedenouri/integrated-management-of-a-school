package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ChangePasswordRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.UpdateProfileRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfileResponse;
import com.gestion.etablissement.scolaire.developpementerp.services.IProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-profile")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Profil Utilisateur (My Profile)", description = "Gestion du profil personnel accessible à tous les utilisateurs connectés")
public class ProfileController {

    private final IProfileService profileService;

    @Operation(summary = "Consulter son propre profil personnel (Tous rôles confondus).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil récupéré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        log.debug("GET /api-profile/me pour : {}", authentication.getName());
        return ResponseEntity.ok(profileService.getProfile(authentication.getName()));
    }

    @Operation(summary = "Mettre à jour ses coordonnées personnelles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil mis à jour avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(Authentication authentication,
                                                           @Valid @RequestBody UpdateProfileRequest request) {
        log.debug("PUT /api-profile/me pour : {}", authentication.getName());
        return ResponseEntity.ok(profileService.updateProfile(authentication.getName(), request));
    }

    @Operation(summary = "Modifier son mot de passe de manière sécurisée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mot de passe modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Ancien mot de passe erroné ou confirmation invalide"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(Authentication authentication,
                                                 @Valid @RequestBody ChangePasswordRequest request) {
        log.debug("POST /api-profile/change-password pour : {}", authentication.getName());
        profileService.changePassword(authentication.getName(), request);
        return ResponseEntity.ok("Mot de passe mis à jour avec succès.");
    }
}
