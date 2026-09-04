package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ClasseRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ClasseResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IClasseService;
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
@RequestMapping("/api-classe")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class ClasseController {

    private final IClasseService classeService;

    @Operation(summary = "Cette opération permet d'ajouter une Classe dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT')")
    @PostMapping("/add-Classe")
    public ResponseEntity<ClasseResponce> addClasse(@Valid @RequestBody ClasseRequest classeRequest) {
        log.debug("add classe: {}", classeRequest.getNom());
        return ResponseEntity.status(HttpStatus.CREATED).body(classeService.addClasse(classeRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les classes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponce.class))
            })
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT', 'RESPONSABLE_FINANCIER')")
    @GetMapping("/getAllClasses")
    public ResponseEntity<List<ClasseResponce>> getAllClasses() {
        log.debug("getAllClasses CONTROLLER");
        return ResponseEntity.ok(classeService.getAllClasses());
    }

    @Operation(summary = "Cette opération permet de récupérer une classe par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT', 'RESPONSABLE_FINANCIER')")
    @GetMapping("/getClasseById/{idClasse}")
    public ResponseEntity<ClasseResponce> getClasseById(@PathVariable("idClasse") Long idClasse) {
        log.debug("getClasseById CONTROLLER - ID: {}", idClasse);
        return ResponseEntity.ok(classeService.getClasseById(idClasse));
    }

    @Operation(summary = "Cette opération permet de modifier une Classe dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ClasseResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT')")
    @PatchMapping("/update-Classe/{idClasse}")
    public ResponseEntity<ClasseResponce> updateClasse(@PathVariable("idClasse") Long idClasse,
                                                       @Valid @RequestBody ClasseRequest classeRequest) {
        log.debug("update Classe ID: {}", idClasse);
        return ResponseEntity.ok(classeService.updateClasse(idClasse, classeRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer une Classe dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT')")
    @DeleteMapping("/delete-Classe/{idClasse}")
    public ResponseEntity<Void> deleteClasse(@PathVariable("idClasse") Long idClasse) {
        log.debug("Delete Classe : {}", idClasse);
        classeService.deleteClasse(idClasse);
        return ResponseEntity.noContent().build();
    }
}
