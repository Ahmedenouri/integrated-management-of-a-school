package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EvaluationRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EvaluationResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IEvaluationService;
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
@RequestMapping("/api-evaluation")
@AllArgsConstructor
@Slf4j
public class EvaluationController {

    private final IEvaluationService evaluationService;

    @Operation(summary = "Cette opération permet d'ajouter une Evaluation dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PostMapping("/add-Evaluation")
    public ResponseEntity<EvaluationResponce> addEvaluation(@Valid @RequestBody EvaluationRequest request) {
        log.debug("add Evaluation: {}", request.getTitre());
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluationService.addEvaluation(request));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les évaluations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationResponce.class))
            })
    })
    @GetMapping("/getAllEvaluations")
    public ResponseEntity<List<EvaluationResponce>> getAllEvaluations() {
        log.debug("getAllEvaluations CONTROLLER");
        return ResponseEntity.ok(evaluationService.getAllEvaluations());
    }

    @Operation(summary = "Cette opération permet de récupérer une évaluation par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @GetMapping("/getEvaluationById/{id}")
    public ResponseEntity<EvaluationResponce> getEvaluationById(@PathVariable("id") Long id) {
        log.debug("getEvaluationById CONTROLLER - ID: {}", id);
        return ResponseEntity.ok(evaluationService.getEvaluationById(id));
    }

    @Operation(summary = "Cette opération permet de modifier une Evaluation dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EvaluationResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PatchMapping("/update-Evaluation/{id}")
    public ResponseEntity<EvaluationResponce> updateEvaluation(@PathVariable("id") Long id,
                                                               @Valid @RequestBody EvaluationRequest request) {
        log.debug("update Evaluation ID: {}", id);
        return ResponseEntity.ok(evaluationService.updateEvaluation(id, request));
    }

    @Operation(summary = "Cette opération permet de supprimer une Evaluation dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @DeleteMapping("/delete-Evaluation/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable("id") Long id) {
        log.debug("Delete Evaluation : {}", id);
        evaluationService.deleteEvaluation(id);
        return ResponseEntity.noContent().build();
    }
}
