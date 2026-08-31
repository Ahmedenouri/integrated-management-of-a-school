package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ResponsableFinancierRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ResponsableFinancierResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IResponsableFinancierService;
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
@RequestMapping("/api-responsable-financier")
@AllArgsConstructor
@Slf4j
public class ResponsableFinancierController {

    private final IResponsableFinancierService responsableFinancierService;

    @Operation(summary = "Cette opération permet d'ajouter un Responsable Financier dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            })
    })
    @PostMapping("/add-ResponsableFinancier")
    public ResponseEntity<ResponsableFinancierResponce> addResponsableFinancier(@Valid @RequestBody ResponsableFinancierRequest request) {
        log.debug("add responsable financier : {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(responsableFinancierService.addResponsableFinancier(request));
    }

    @Operation(summary = "Cette opération permet de récupérer tous les responsables financiers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            })
    })
    @GetMapping("/getAllResponsablesFinanciers")
    public ResponseEntity<List<ResponsableFinancierResponce>> getAllResponsablesFinanciers() {
        log.debug("getAllResponsablesFinanciers CONTROLLER");
        return ResponseEntity.ok(responsableFinancierService.getAllResponsablesFinanciers());
    }

    @Operation(summary = "Cette opération permet de récupérer un responsable financier par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getResponsableFinancierById/{id}")
    public ResponseEntity<ResponsableFinancierResponce> getResponsableFinancierById(@PathVariable("id") Long id) {
        log.debug("getResponsableFinancierById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(responsableFinancierService.getResponsableFinancierById(id));
    }

    @Operation(summary = "Cette opération permet de modifier un Responsable Financier dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponsableFinancierResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-ResponsableFinancier/{id}")
    public ResponseEntity<ResponsableFinancierResponce> updateResponsableFinancier(@PathVariable("id") Long id,
                                                                                   @Valid @RequestBody ResponsableFinancierRequest request) {
        log.debug("update ResponsableFinancier ID: {}", id);
        return ResponseEntity.ok(responsableFinancierService.updateResponsableFinancier(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer un Responsable Financier dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-ResponsableFinancier/{id}")
    public ResponseEntity<Void> deleteResponsableFinancier(@PathVariable("id") Long id) {
        log.debug("Delete ResponsableFinancier ID: {}", id);
        responsableFinancierService.deleteResponsableFinancier(id);
        return ResponseEntity.noContent().build();
    }
}
