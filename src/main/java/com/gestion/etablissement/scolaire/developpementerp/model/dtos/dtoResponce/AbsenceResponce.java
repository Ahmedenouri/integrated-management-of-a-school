package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsenceResponce {

    private Long id;
    private LocalDate dateAbsence;
    private Boolean estJustifiee;
    private String motifJustification;
    private Integer nombreHeures;
    private Long seanceId;
    private Long etudiantId;
    private Long surveillantId;
}
