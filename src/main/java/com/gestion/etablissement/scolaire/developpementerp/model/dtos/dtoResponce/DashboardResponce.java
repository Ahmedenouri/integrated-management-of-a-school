package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponce {

    // Stats Pédagogiques
    private Long totalEtudiants;
    private Long totalClasses;
    private Long totalMatieres;
    private Double moyenneEtablissement;

    // Stats Disciplinaires
    private Long totalAbsences;
    private Integer totalHeuresAbsences;
    private Long totalSanctions;
    private Map<String, Long> sanctionsParType;

    // Stats Financières
    private Double totalEncaissementPercu;
    private Double totalImpayes;
    private Long nombreEtudiantsEnRetard;
}
