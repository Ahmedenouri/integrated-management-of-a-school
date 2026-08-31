package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ProfesseurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfesseurResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IProfesseurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-professeur")
@AllArgsConstructor
@Slf4j
public class ProfesseurController {

    private final IProfesseurService professeurService;

    @Operation(summary = "Cette opération permet d'ajouter un Professeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            })
    })
    @PostMapping("/add-Professeur")
    public ResponseEntity<ProfesseurResponce> addProfesseur(@RequestBody ProfesseurRequest professeurRequest) {
        log.debug("add professeur : {}", professeurRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(professeurService.addProfesseur(professeurRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les professeurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            })
    })
    @GetMapping("/getAllProfesseurs")
    public ResponseEntity<List<ProfesseurResponce>> getAllProfesseurs() {
        log.debug("getAllProfesseurs CONTROLLER");
        return ResponseEntity.ok(professeurService.getAllProfesseurs());
    }

    @Operation(summary = "Cette opération permet de récupérer un professeur par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            })
    })
    @GetMapping("/getProfesseurById/{idProfesseur}")
    public ResponseEntity<ProfesseurResponce> getProfesseurById(@PathVariable("idProfesseur") Long idProfesseur) {
        log.debug("getProfesseurById CONTROLLER - ID: {}", idProfesseur);
        return ResponseEntity.ok(professeurService.getProfesseurById(idProfesseur));
    }

    @Operation(summary = "Cette opération permet de modifier un Professeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            })
    })
    @PatchMapping("/update-Professeur/{idProfesseur}")
    public ResponseEntity<ProfesseurResponce> updateProfesseur(@PathVariable("idProfesseur") Long idProfesseur,
                                                               @RequestBody ProfesseurRequest professeurRequest) {
        log.debug("update Professeur with email: {} - ID: {}", professeurRequest.getEmail(), idProfesseur);
        return ResponseEntity.ok(professeurService.updateProfesseur(idProfesseur, professeurRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer un Professeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfesseurResponce.class))
            })
    })
    @DeleteMapping("/delete-Professeur/{idProfesseur}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable("idProfesseur") Long idProfesseur) {
        log.debug("Delete Professeur : {}", idProfesseur);
        professeurService.deleteProfesseur(idProfesseur);
        return ResponseEntity.noContent().build();
    }
}
