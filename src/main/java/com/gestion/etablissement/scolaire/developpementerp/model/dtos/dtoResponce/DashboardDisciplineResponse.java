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
public class DashboardDisciplineResponse {
    // Statistiques Globales & Disciplinaires autorisées pour le Surveillant
    private Long totalEtudiants;
    private Long totalClasses;
    private Long totalMatieres;
    private Double moyenneEtablissement;
    private Long totalAbsences;
    private Integer totalHeuresAbsences;
    private Long totalSanctions;
    private Map<String, Long> sanctionsParType;
}
