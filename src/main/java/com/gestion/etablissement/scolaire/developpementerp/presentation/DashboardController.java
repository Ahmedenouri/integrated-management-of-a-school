package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardDisciplineResponse;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardFinancierResponse;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.IDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-dashboard")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@Tag(name = "Dashboard", description = "Tableaux de bord cloisonnés par profil")
public class DashboardController {

    private final IDashboardService dashboardService;

    @Operation(summary = "Récupérer les statistiques globales agrégées (Directeur uniquement).")
    @PreAuthorize("hasRole('DIRECTEUR')")
    @GetMapping("/stats")
    public ResponseEntity<DashboardResponce> getDashboardStats() {
        log.debug("GET /api-dashboard/stats CONTROLLER");
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @Operation(summary = "Récupérer les statistiques financières (Directeur et Responsable Financier).")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'RESPONSABLE_FINANCIER')")
    @GetMapping("/financier")
    public ResponseEntity<DashboardFinancierResponse> getDashboardFinancier() {
        log.debug("GET /api-dashboard/financier CONTROLLER");
        return ResponseEntity.ok(dashboardService.getDashboardFinancierStats());
    }

    @Operation(summary = "Récupérer les statistiques disciplinaires (Directeur et Surveillant).")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT')")
    @GetMapping("/discipline")
    public ResponseEntity<DashboardDisciplineResponse> getDashboardDiscipline() {
        log.debug("GET /api-dashboard/discipline CONTROLLER");
        return ResponseEntity.ok(dashboardService.getDashboardDisciplineStats());
    }
}
