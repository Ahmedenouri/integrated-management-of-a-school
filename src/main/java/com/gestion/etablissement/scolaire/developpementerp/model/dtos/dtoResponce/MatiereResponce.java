package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatiereResponce {

    private Long id;
    private String code;
    private String intitule;
    private Double coefficient;
    private Integer volumeHoraire;
}
