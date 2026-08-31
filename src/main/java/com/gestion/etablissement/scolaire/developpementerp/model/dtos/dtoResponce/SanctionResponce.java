package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeSanction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanctionResponce {

    private Long id;
    private LocalDate dateEmission;
    private TypeSanction type;
    private String motif;
    private Integer totalAbsencesAuMoment;
    private Boolean estTraitee;
    private Long etudiantId;
    private Long surveillantId;
}
