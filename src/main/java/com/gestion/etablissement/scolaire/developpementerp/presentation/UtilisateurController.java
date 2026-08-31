package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.UtilisateurResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IUtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-user")
@AllArgsConstructor
@Slf4j
public class UtilisateurContriller {
    private final IUtilisateurService utilisateurService;

    @Operation(summary = "Cette opération permet selection tout les utilisateur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "L'opération est effectuée avec succés",content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = UtilisateurContriller.class))

            }),
            @ApiResponse(responseCode = "400",description = "La requete envoyée incorrect. Bad Request !",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurContriller.class))
            }),
            @ApiResponse(responseCode = "401",description = "l'utilisateur n'est pas authentifié",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurContriller.class))
            }),
            @ApiResponse(responseCode = "403",description = "l'utilisateur n'est pas authorisé à faire l'action demandé",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurContriller.class))
            }),
            @ApiResponse(responseCode = "404",description = "la resource demandée est introuvable. Not Found !",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurContriller.class))
            }),
            @ApiResponse(responseCode = "500",description = "Erreur Server !",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurContriller.class))
            })
    })
    @GetMapping("/getAllUsers")
    public List<UtilisateurResponce> getAllUsers() {
        log.debug("getAllUtilisateurs CONTROLLER");
        return utilisateurService.getAllUsers();
    }
}
