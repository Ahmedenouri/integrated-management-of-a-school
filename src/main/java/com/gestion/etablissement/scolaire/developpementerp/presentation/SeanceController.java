package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SeanceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SeanceResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.ISeanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-seance")
@AllArgsConstructor
@Slf4j
public class SeanceController {

    private final ISeanceService seanceService;

    @Operation(summary = "Cette opération permet d'ajouter une Seance dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeanceResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-Seance")
    public ResponseEntity<SeanceResponce> addSeance(@Valid @RequestBody SeanceRequest request) {
        log.debug("add Seance for emploi ID: {}", request.getEmploiDuTempsId());
        return ResponseEntity.status(HttpStatus.CREATED).body(seanceService.addSeance(request));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les séances.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeanceResponce.class))
            })
    })
    @GetMapping("/getAllSeances")
    public ResponseEntity<List<SeanceResponce>> getAllSeances() {
        log.debug("getAllSeances CONTROLLER");
        return ResponseEntity.ok(seanceService.getAllSeances());
    }

    @Operation(summary = "Cette opération permet de récupérer une séance par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeanceResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getSeanceById/{id}")
    public ResponseEntity<SeanceResponce> getSeanceById(@PathVariable("id") Long id) {
        log.debug("getSeanceById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(seanceService.getSeanceById(id));
    }

    @Operation(summary = "Cette opération permet de modifier une Seance dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeanceResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-Seance/{id}")
    public ResponseEntity<SeanceResponce> updateSeance(@PathVariable("id") Long id,
                                                       @Valid @RequestBody SeanceRequest request) {
        log.debug("update Seance ID: {}", id);
        return ResponseEntity.ok(seanceService.updateSeance(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer une Seance dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-Seance/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable("id") Long id) {
        log.debug("Delete Seance : {}", id);
        seanceService.deleteSeance(id);
        return ResponseEntity.noContent().build();
    }
}
