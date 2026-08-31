package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EtudiantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EtudiantResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IEtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-etudiant")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class EtudiantController {

    private final IEtudiantService etudiantService;

    @Operation(summary = "Cette opération permet d'ajouter un Etudiant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            })
    })
    @PostMapping("/add-Etudiant")
    public ResponseEntity<EtudiantResponce> addEtudiant(@RequestBody EtudiantRequest etudiantRequest) {
        log.debug("add etudiant : {}", etudiantRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantService.addEtudiant(etudiantRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les étudiants.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            })
    })
    @GetMapping("/getAllEtudiants")
    public ResponseEntity<List<EtudiantResponce>> getAllEtudiants() {
        log.debug("getAllEtudiants CONTROLLER");
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    @Operation(summary = "Cette opération permet de récupérer un étudiant par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            })
    })
    @GetMapping("/getEtudiantById/{idEtudiant}")
    public ResponseEntity<EtudiantResponce> getEtudiantById(@PathVariable("idEtudiant") Long idEtudiant) {
        log.debug("getEtudiantById CONTROLLER - ID: {}", idEtudiant);
        return ResponseEntity.ok(etudiantService.getEtudiantById(idEtudiant));
    }

    @Operation(summary = "Cette opération permet de modifier un Etudiant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            })
    })
    @PatchMapping("/update-Etudiant/{idEtudiant}")
    public ResponseEntity<EtudiantResponce> updateEtudiant(@PathVariable("idEtudiant") Long idEtudiant,
                                                             @RequestBody EtudiantRequest etudiantRequest) {
        log.debug("update Etudiant with email: {} - ID: {}", etudiantRequest.getEmail(), idEtudiant);
        return ResponseEntity.ok(etudiantService.updateEtudiant(idEtudiant, etudiantRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer un Etudiant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantResponce.class))
            })
    })
    @DeleteMapping("/delete-Etudiant/{idEtudiant}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        log.debug("Delete Etudiant : {}", idEtudiant);
        etudiantService.deleteEtudiant(idEtudiant);
        return ResponseEntity.noContent().build();
    }
}
