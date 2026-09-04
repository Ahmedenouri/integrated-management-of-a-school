package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.MatiereRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.MatiereResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IMatiereService;
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
@RequestMapping("/api-matiere")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class MatiereController {

    private final IMatiereService matiereService;

    @Operation(summary = "Cette opération permet d'ajouter une Matiere dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatiereResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasRole('DIRECTEUR')")
    @PostMapping("/add-Matiere")
    public ResponseEntity<MatiereResponce> addMatiere(@Valid @RequestBody MatiereRequest request) {
        log.debug("add Matiere: {}", request.getIntitule());
        return ResponseEntity.status(HttpStatus.CREATED).body(matiereService.addMatiere(request));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les matières.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatiereResponce.class))
            })
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT', 'PROFESSEUR', 'ETUDIANT')")
    @GetMapping("/getAllMatieres")
    public ResponseEntity<List<MatiereResponce>> getAllMatieres() {
        log.debug("getAllMatieres CONTROLLER");
        return ResponseEntity.ok(matiereService.getAllMatieres());
    }

    @Operation(summary = "Cette opération permet de récupérer une matière par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatiereResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT', 'PROFESSEUR', 'ETUDIANT')")
    @GetMapping("/getMatiereById/{id}")
    public ResponseEntity<MatiereResponce> getMatiereById(@PathVariable("id") Long id) {
        log.debug("getMatiereById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(matiereService.getMatiereById(id));
    }

    @Operation(summary = "Cette opération permet de modifier une Matiere dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MatiereResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasRole('DIRECTEUR')")
    @PatchMapping("/update-Matiere/{id}")
    public ResponseEntity<MatiereResponce> updateMatiere(@PathVariable("id") Long id,
                                                         @Valid @RequestBody MatiereRequest request) {
        log.debug("update Matiere ID: {}", id);
        return ResponseEntity.ok(matiereService.updateMatiere(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer une Matiere dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasRole('DIRECTEUR')")
    @DeleteMapping("/delete-Matiere/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable("id") Long id) {
        log.debug("Delete Matiere : {}", id);
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }
}
