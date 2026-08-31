package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-dashboard")
@AllArgsConstructor
@Slf4j
public class DashboardController {

    private final IDashboardService dashboardService;

    @Operation(summary = "Récupérer les statistiques agrégées en temps réel (Tableau de bord Pédagogique, Disciplinaire et Financier).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DashboardResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Serveur !")
    })
    @GetMapping("/stats")
    public ResponseEntity<DashboardResponce> getDashboardStats() {
        log.debug("GET /api-dashboard/stats CONTROLLER");
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }
}
