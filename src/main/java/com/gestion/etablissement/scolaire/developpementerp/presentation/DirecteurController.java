package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.DirecteurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DirecteurResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IDirecteurService;
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
@RequestMapping("/api-directeur")
@AllArgsConstructor
@Slf4j
public class DirecteurController {

    private final IDirecteurService directeurService;

    @Operation(summary = "Cette opération permet d'ajouter un Directeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            })
    })
    @PostMapping("/add-Directeur")
    public ResponseEntity<DirecteurResponce> addDirecteur(@RequestBody DirecteurRequest directeurRequest) {
        log.debug("add directeur : {}", directeurRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(directeurService.addDirecteur(directeurRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les directeurs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            })
    })
    @GetMapping("/getAllDirecteurs")
    public ResponseEntity<List<DirecteurResponce>> getAllDirecteurs() {
        log.debug("getAllDirecteurs CONTROLLER");
        return ResponseEntity.ok(directeurService.getAllDirecteurs());
    }

    @Operation(summary = "Cette opération permet de récupérer un directeur par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            })
    })
    @GetMapping("/getDirecteurById/{idDirecteur}")
    public ResponseEntity<DirecteurResponce> getDirecteurById(@PathVariable("idDirecteur") Long idDirecteur) {
        log.debug("getDirecteurById CONTROLLER - ID: {}", idDirecteur);
        return ResponseEntity.ok(directeurService.getDirecteurById(idDirecteur));
    }

    @Operation(summary = "Cette opération permet de modifier un Directeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            })
    })
    @PatchMapping("/update-Directeur/{idDirecteur}")
    public ResponseEntity<DirecteurResponce> updateDirecteur(@PathVariable("idDirecteur") Long idDirecteur,
                                                             @RequestBody DirecteurRequest directeurRequest) {
        log.debug("update Directeur with email: {} - ID: {}", directeurRequest.getEmail(), idDirecteur);
        return ResponseEntity.ok(directeurService.updateDirecteur(idDirecteur, directeurRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer un Directeur dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DirecteurResponce.class))
            })
    })
    @DeleteMapping("/delete-Directeur/{idDirecteur}")
    public ResponseEntity<Void> deleteDirecteur(@PathVariable("idDirecteur") Long idDirecteur) {
        log.debug("Delete Directeur : {}", idDirecteur);
        directeurService.deleteDirecteur(idDirecteur);
        return ResponseEntity.noContent().build();
    }
}
