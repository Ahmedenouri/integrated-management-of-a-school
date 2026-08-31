package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

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
public class AbsenceRequest {

    @NotNull(message = "La date d'absence est obligatoire")
    private LocalDate dateAbsence;

    private Boolean estJustifiee = false;
    private String motifJustification;
    private Integer nombreHeures;

    @NotNull(message = "L'ID de la séance est obligatoire")
    private Long seanceId;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private Long etudiantId;

    private Long surveillantId;
}
