package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.PaiementRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.PaiementResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IPaiementService;
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
@RequestMapping("/api-paiement")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "basicAuth")
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Cette opération permet d'ajouter un Paiement dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER')")
    @PostMapping("/add-Paiement")
    public ResponseEntity<PaiementResponce> addPaiement(@Valid @RequestBody PaiementRequest request) {
        log.debug("add Paiement ref: {}", request.getReferencePaiement());
        return ResponseEntity.status(HttpStatus.CREATED).body(paiementService.addPaiement(request));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les paiements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER')")
    @GetMapping("/getAllPaiements")
    public ResponseEntity<List<PaiementResponce>> getAllPaiements() {
        log.debug("getAllPaiements CONTROLLER");
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    @Operation(summary = "Cette opération permet de récupérer un paiement par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PaiementResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER') or @securityService.isPaiementOwner(#id)")
    @GetMapping("/getPaiementById/{id}")
    public ResponseEntity<PaiementResponce> getPaiementById(@PathVariable("id") Long id) {
        log.debug("getPaiementById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    @Operation(summary = "Consulter la liste de ses propres paiements (Espace Étudiant).")
    @PreAuthorize("hasRole('ETUDIANT')")
    @GetMapping("/mes-paiements")
    public ResponseEntity<List<PaiementResponce>> getMesPaiements(Authentication authentication) {
        log.debug("getMesPaiements pour étudiant : {}", authentication.getName());
        return ResponseEntity.ok(paiementService.getPaiementsByEtudiantEmail(authentication.getName()));
    }

    @Operation(summary = "Cette opération permet de modifier un Paiement dans la base.")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER')")
    @PatchMapping("/update-Paiement/{id}")
    public ResponseEntity<PaiementResponce> updatePaiement(@PathVariable("id") Long id,
                                                           @Valid @RequestBody PaiementRequest request) {
        log.debug("update Paiement ID: {}", id);
        return ResponseEntity.ok(paiementService.updatePaiement(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer un Paiement dans la base.")
    @PreAuthorize("hasRole('DIRECTEUR')")
    @DeleteMapping("/delete-Paiement/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable("id") Long id) {
        log.debug("Delete Paiement : {}", id);
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Récupérer la liste des paiements en retard ou partiels (Tableau de bord des impayés).")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER')")
    @GetMapping("/impayes")
    public ResponseEntity<List<PaiementResponce>> getImpayes() {
        log.debug("getImpayes CONTROLLER");
        return ResponseEntity.ok(paiementService.getImpayes());
    }

    @Operation(summary = "Générer et télécharger le reçu de paiement officiel en format PDF.")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER') or @securityService.isPaiementOwner(#paiementId)")
    @GetMapping(value = "/recu-pdf/{paiementId}", produces = org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateRecuPdf(@PathVariable("paiementId") Long paiementId) {
        log.debug("generateRecuPdf - Paiement ID: {}", paiementId);
        byte[] pdf = paiementService.generateRecuPdf(paiementId);
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recu_" + paiementId + ".pdf")
                .body(pdf);
    }
}
