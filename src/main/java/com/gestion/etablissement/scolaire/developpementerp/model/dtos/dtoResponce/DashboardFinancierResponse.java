package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardFinancierResponse {
    // Statistiques Globales & Financières autorisées pour le Responsable Financier
    private Long totalEtudiants;
    private Long totalClasses;
    private Double totalEncaissementPercu;
    private Double totalImpayes;
    private Long nombreEtudiantsEnRetard;
}
