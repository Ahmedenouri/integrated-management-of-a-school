package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.RecuRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.RecuResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IRecuService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-recu")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('RESPONSABLE_FINANCIER', 'DIRECTEUR', 'ETUDIANT')")
public class RecuController {

    private final IRecuService recuService;

    @Operation(summary = "Cette opération permet d'ajouter un Recu dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecuResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasAnyRole('RESPONSABLE_FINANCIER', 'DIRECTEUR')")
    @PostMapping("/add-Recu")
    public ResponseEntity<RecuResponce> addRecu(@Valid @RequestBody RecuRequest request) {
        log.debug("add Recu: {}", request.getNumeroRecu());
        return ResponseEntity.status(HttpStatus.CREATED).body(recuService.addRecu(request));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les reçus.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecuResponce.class))
            })
    })
    @GetMapping("/getAllRecus")
    public ResponseEntity<List<RecuResponce>> getAllRecus() {
        log.debug("getAllRecus CONTROLLER");
        return ResponseEntity.ok(recuService.getAllRecus());
    }

    @Operation(summary = "Cette opération permet de récupérer un reçu par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecuResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getRecuById/{id}")
    public ResponseEntity<RecuResponce> getRecuById(@PathVariable("id") Long id) {
        log.debug("getRecuById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(recuService.getRecuById(id));
    }

    @Operation(summary = "Cette opération permet de modifier un Recu dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecuResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('RESPONSABLE_FINANCIER', 'DIRECTEUR')")
    @PatchMapping("/update-Recu/{id}")
    public ResponseEntity<RecuResponce> updateRecu(@PathVariable("id") Long id,
                                                   @Valid @RequestBody RecuRequest request) {
        log.debug("update Recu ID: {}", id);
        return ResponseEntity.ok(recuService.updateRecu(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer un Recu dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasRole('DIRECTEUR')")
    @DeleteMapping("/delete-Recu/{id}")
    public ResponseEntity<Void> deleteRecu(@PathVariable("id") Long id) {
        log.debug("Delete Recu : {}", id);
        recuService.deleteRecu(id);
        return ResponseEntity.noContent().build();
    }
}
