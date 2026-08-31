package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.BulletinRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.BulletinResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IBulletinService;
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
@RequestMapping("/api-bulletin")
@AllArgsConstructor
@Slf4j
public class BulletinController {

    private final IBulletinService bulletinService;

    @Operation(summary = "Cette opération permet d'ajouter un Bulletin dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BulletinResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-Bulletin")
    public ResponseEntity<BulletinResponce> addBulletin(@Valid @RequestBody BulletinRequest bulletinRequest) {
        log.debug("add bulletin for etudiant ID: {}", bulletinRequest.getEtudiantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(bulletinService.addBulletin(bulletinRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les bulletins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BulletinResponce.class))
            })
    })
    @GetMapping("/getAllBulletins")
    public ResponseEntity<List<BulletinResponce>> getAllBulletins() {
        log.debug("getAllBulletins CONTROLLER");
        return ResponseEntity.ok(bulletinService.getAllBulletins());
    }

    @Operation(summary = "Cette opération permet de récupérer un bulletin par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BulletinResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getBulletinById/{idBulletin}")
    public ResponseEntity<BulletinResponce> getBulletinById(@PathVariable("idBulletin") Long idBulletin) {
        log.debug("getBulletinById CONTROLLER - ID: {}", idBulletin);
        return ResponseEntity.ok(bulletinService.getBulletinById(idBulletin));
    }

    @Operation(summary = "Cette opération permet de modifier un Bulletin dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BulletinResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-Bulletin/{idBulletin}")
    public ResponseEntity<BulletinResponce> updateBulletin(@PathVariable("idBulletin") Long idBulletin,
                                                           @Valid @RequestBody BulletinRequest bulletinRequest) {
        log.debug("update Bulletin ID: {}", idBulletin);
        return ResponseEntity.ok(bulletinService.updateBulletin(idBulletin, bulletinRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer un Bulletin dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-Bulletin/{idBulletin}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable("idBulletin") Long idBulletin) {
        log.debug("Delete Bulletin : {}", idBulletin);
        bulletinService.deleteBulletin(idBulletin);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Générer et télécharger le bulletin officiel en format PDF pour un étudiant.")
    @GetMapping(value = "/pdf/{etudiantId}", produces = org.springframework.http.MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateBulletinPdf(@PathVariable("etudiantId") Long etudiantId) {
        log.debug("generateBulletinPdf - Etudiant ID: {}", etudiantId);
        byte[] pdf = bulletinService.generateBulletinPdf(etudiantId);
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline; filename=bulletin_" + etudiantId + ".pdf")
                .body(pdf);
    }
}
