package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EmploiDuTempsRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EmploiDuTempsResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IEmploiDuTempsService;
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
@RequestMapping("/api-emploi-du-temps")
@AllArgsConstructor
@Slf4j
public class EmploiDuTempsController {

    private final IEmploiDuTempsService emploiDuTempsService;

    @Operation(summary = "Cette opération permet d'ajouter un EmploiDuTemps dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmploiDuTempsResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-EmploiDuTemps")
    public ResponseEntity<EmploiDuTempsResponce> addEmploiDuTemps(@Valid @RequestBody EmploiDuTempsRequest request) {
        log.debug("add EmploiDuTemps for classe ID: {}", request.getClasseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(emploiDuTempsService.addEmploiDuTemps(request));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les emplois du temps.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmploiDuTempsResponce.class))
            })
    })
    @GetMapping("/getAllEmploisDuTemps")
    public ResponseEntity<List<EmploiDuTempsResponce>> getAllEmploisDuTemps() {
        log.debug("getAllEmploisDuTemps CONTROLLER");
        return ResponseEntity.ok(emploiDuTempsService.getAllEmploisDuTemps());
    }

    @Operation(summary = "Cette opération permet de récupérer un emploi du temps par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmploiDuTempsResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getEmploiDuTempsById/{id}")
    public ResponseEntity<EmploiDuTempsResponce> getEmploiDuTempsById(@PathVariable("id") Long id) {
        log.debug("getEmploiDuTempsById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(emploiDuTempsService.getEmploiDuTempsById(id));
    }

    @Operation(summary = "Cette opération permet de modifier un EmploiDuTemps dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmploiDuTempsResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-EmploiDuTemps/{id}")
    public ResponseEntity<EmploiDuTempsResponce> updateEmploiDuTemps(@PathVariable("id") Long id,
                                                                     @Valid @RequestBody EmploiDuTempsRequest request) {
        log.debug("update EmploiDuTemps ID: {}", id);
        return ResponseEntity.ok(emploiDuTempsService.updateEmploiDuTemps(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer un EmploiDuTemps dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-EmploiDuTemps/{id}")
    public ResponseEntity<Void> deleteEmploiDuTemps(@PathVariable("id") Long id) {
        log.debug("Delete EmploiDuTemps : {}", id);
        emploiDuTempsService.deleteEmploiDuTemps(id);
        return ResponseEntity.noContent().build();
    }
}
