package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeEvaluation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationRequest {

    @NotBlank(message = "Le titre de l'évaluation est obligatoire")
    private String titre;

    @NotNull(message = "Le type d'évaluation est obligatoire")
    private TypeEvaluation typeEval;

    @NotNull(message = "La date d'évaluation est obligatoire")
    private LocalDate dateEvaluation;

    @NotNull(message = "Le coefficient est obligatoire")
    private Double coefficient;

    @NotNull(message = "L'ID de la matière est obligatoire")
    private Long matiereId;
}
