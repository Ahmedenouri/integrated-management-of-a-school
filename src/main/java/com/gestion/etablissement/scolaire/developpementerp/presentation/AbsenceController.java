package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.AbsenceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.AbsenceResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IAbsenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-absence")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "basicAuth")
public class AbsenceController {

    private final IAbsenceService absenceService;

    @Operation(summary = "Cette opération permet d'ajouter une Absence dans la base (Surveillant ou Directeur).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasAnyRole('SURVEILLANT', 'DIRECTEUR')")
    @PostMapping("/add-Absence")
    public ResponseEntity<AbsenceResponce> addAbsence(@Valid @RequestBody AbsenceRequest absenceRequest) {
        log.debug("add absence for etudiant ID: {}", absenceRequest.getEtudiantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(absenceService.addAbsence(absenceRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les absences (Surveillant et Directeur uniquement).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasAnyRole('SURVEILLANT', 'DIRECTEUR')")
    @GetMapping("/getAllAbsences")
    public ResponseEntity<List<AbsenceResponce>> getAllAbsences() {
        log.debug("getAllAbsences CONTROLLER");
        return ResponseEntity.ok(absenceService.getAllAbsences());
    }

    @Operation(summary = "Cette opération permet de récupérer une absence par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('SURVEILLANT', 'DIRECTEUR') or @securityService.isAbsenceOwner(#idAbsence)")
    @GetMapping("/getAbsenceById/{idAbsence}")
    public ResponseEntity<AbsenceResponce> getAbsenceById(@PathVariable("idAbsence") Long idAbsence) {
        log.debug("getAbsenceById CONTROLLER - ID: {}", idAbsence);
        return ResponseEntity.ok(absenceService.getAbsenceById(idAbsence));
    }

    @Operation(summary = "Consulter ses propres absences (Espace Étudiant).")
    @PreAuthorize("hasRole('ETUDIANT')")
    @GetMapping("/mes-absences")
    public ResponseEntity<List<AbsenceResponce>> getMesAbsences(Authentication authentication) {
        log.debug("getMesAbsences pour étudiant : {}", authentication.getName());
        return ResponseEntity.ok(absenceService.getAbsencesByEtudiantEmail(authentication.getName()));
    }

    @Operation(summary = "Cette opération permet de modifier une Absence dans la base.")
    @PreAuthorize("hasAnyRole('SURVEILLANT', 'DIRECTEUR')")
    @PatchMapping("/update-Absence/{idAbsence}")
    public ResponseEntity<AbsenceResponce> updateAbsence(@PathVariable("idAbsence") Long idAbsence,
                                                         @Valid @RequestBody AbsenceRequest absenceRequest) {
        log.debug("update Absence ID: {}", idAbsence);
        return ResponseEntity.ok(absenceService.updateAbsence(idAbsence, absenceRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer une Absence dans la base.")
    @PreAuthorize("hasRole('DIRECTEUR')")
    @DeleteMapping("/delete-Absence/{idAbsence}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable("idAbsence") Long idAbsence) {
        log.debug("Delete Absence : {}", idAbsence);
        absenceService.deleteAbsence(idAbsence);
        return ResponseEntity.noContent().build();
    }
}
