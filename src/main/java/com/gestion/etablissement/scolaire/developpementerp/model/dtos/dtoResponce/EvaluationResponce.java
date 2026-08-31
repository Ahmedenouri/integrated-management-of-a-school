package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeEvaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationResponce {

    private Long id;
    private String titre;
    private TypeEvaluation typeEval;
    private LocalDate dateEvaluation;
    private Double coefficient;
    private Long matiereId;
}
