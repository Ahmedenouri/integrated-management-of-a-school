package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SalleRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SalleResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.ISalleService;
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
@RequestMapping("/api-salle")
@AllArgsConstructor
@Slf4j
public class SalleController {

    private final ISalleService salleService;

    @Operation(summary = "Cette opération permet d'ajouter une Salle dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SalleResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-Salle")
    public ResponseEntity<SalleResponce> addSalle(@Valid @RequestBody SalleRequest request) {
        log.debug("add Salle: {}", request.getCodeSalle());
        return ResponseEntity.status(HttpStatus.CREATED).body(salleService.addSalle(request));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les salles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SalleResponce.class))
            })
    })
    @GetMapping("/getAllSalles")
    public ResponseEntity<List<SalleResponce>> getAllSalles() {
        log.debug("getAllSalles CONTROLLER");
        return ResponseEntity.ok(salleService.getAllSalles());
    }

    @Operation(summary = "Cette opération permet de récupérer une salle par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SalleResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getSalleById/{id}")
    public ResponseEntity<SalleResponce> getSalleById(@PathVariable("id") Long id) {
        log.debug("getSalleById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(salleService.getSalleById(id));
    }

    @Operation(summary = "Cette opération permet de modifier une Salle dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SalleResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-Salle/{id}")
    public ResponseEntity<SalleResponce> updateSalle(@PathVariable("id") Long id,
                                                     @Valid @RequestBody SalleRequest request) {
        log.debug("update Salle ID: {}", id);
        return ResponseEntity.ok(salleService.updateSalle(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer une Salle dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-Salle/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable("id") Long id) {
        log.debug("Delete Salle : {}", id);
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }
}
