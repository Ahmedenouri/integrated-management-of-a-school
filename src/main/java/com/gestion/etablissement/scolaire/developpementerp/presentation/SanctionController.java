package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SanctionRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SanctionResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.ISanctionService;
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
@RequestMapping("/api-sanction")
@AllArgsConstructor
@Slf4j
public class SanctionController {

    private final ISanctionService sanctionService;

    @Operation(summary = "Cette opération permet d'ajouter une Sanction dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SanctionResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-Sanction")
    public ResponseEntity<SanctionResponce> addSanction(@Valid @RequestBody SanctionRequest request) {
        log.debug("add Sanction for etudiant ID: {}", request.getEtudiantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(sanctionService.addSanction(request));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les sanctions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SanctionResponce.class))
            })
    })
    @GetMapping("/getAllSanctions")
    public ResponseEntity<List<SanctionResponce>> getAllSanctions() {
        log.debug("getAllSanctions CONTROLLER");
        return ResponseEntity.ok(sanctionService.getAllSanctions());
    }

    @Operation(summary = "Cette opération permet de récupérer une sanction par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SanctionResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getSanctionById/{id}")
    public ResponseEntity<SanctionResponce> getSanctionById(@PathVariable("id") Long id) {
        log.debug("getSanctionById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(sanctionService.getSanctionById(id));
    }

    @Operation(summary = "Cette opération permet de modifier une Sanction dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SanctionResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-Sanction/{id}")
    public ResponseEntity<SanctionResponce> updateSanction(@PathVariable("id") Long id,
                                                           @Valid @RequestBody SanctionRequest request) {
        log.debug("update Sanction ID: {}", id);
        return ResponseEntity.ok(sanctionService.updateSanction(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer une Sanction dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-Sanction/{id}")
    public ResponseEntity<Void> deleteSanction(@PathVariable("id") Long id) {
        log.debug("Delete Sanction : {}", id);
        sanctionService.deleteSanction(id);
        return ResponseEntity.noContent().build();
    }
}
