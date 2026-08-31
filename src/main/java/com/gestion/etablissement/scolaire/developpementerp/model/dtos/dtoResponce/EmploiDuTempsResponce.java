package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmploiDuTempsResponce {

    private Long id;
    private Integer semestre;
    private Boolean estValide;
    private Long classeId;
    private Long surveillantId;
}
