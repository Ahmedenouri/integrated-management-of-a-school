package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SurveillantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SurveillantResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.ISurveillantService;
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
@RequestMapping("/api-surveillant")
@AllArgsConstructor
@Slf4j
public class SurveillantController {

    private final ISurveillantService surveillantService;

    @Operation(summary = "Cette opération permet d'ajouter un Surveillant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            })
    })
    @PostMapping("/add-Surveillant")
    public ResponseEntity<SurveillantResponce> addSurveillant(@RequestBody SurveillantRequest surveillantRequest) {
        log.debug("add surveillant : {}", surveillantRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(surveillantService.addSurveillant(surveillantRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les surveillants.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            })
    })
    @GetMapping("/getAllSurveillants")
    public ResponseEntity<List<SurveillantResponce>> getAllSurveillants() {
        log.debug("getAllSurveillants CONTROLLER");
        return ResponseEntity.ok(surveillantService.getAllSurveillants());
    }

    @Operation(summary = "Cette opération permet de récupérer un surveillant par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            })
    })
    @GetMapping("/getSurveillantById/{idSurveillant}")
    public ResponseEntity<SurveillantResponce> getSurveillantById(@PathVariable("idSurveillant") Long idSurveillant) {
        log.debug("getSurveillantById CONTROLLER - ID: {}", idSurveillant);
        return ResponseEntity.ok(surveillantService.getSurveillantById(idSurveillant));
    }

    @Operation(summary = "Cette opération permet de modifier un Surveillant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            })
    })
    @PatchMapping("/update-Surveillant/{idSurveillant}")
    public ResponseEntity<SurveillantResponce> updateSurveillant(@PathVariable("idSurveillant") Long idSurveillant,
                                                                 @RequestBody SurveillantRequest surveillantRequest) {
        log.debug("update Surveillant with email: {} - ID: {}", surveillantRequest.getEmail(), idSurveillant);
        return ResponseEntity.ok(surveillantService.updateSurveillant(idSurveillant, surveillantRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer un Surveillant dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SurveillantResponce.class))
            })
    })
    @DeleteMapping("/delete-Surveillant/{idSurveillant}")
    public ResponseEntity<Void> deleteSurveillant(@PathVariable("idSurveillant") Long idSurveillant) {
        log.debug("Delete Surveillant : {}", idSurveillant);
        surveillantService.deleteSurveillant(idSurveillant);
        return ResponseEntity.noContent().build();
    }
}
