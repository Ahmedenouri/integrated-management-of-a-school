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
public class NoteResponce {

    private Long id;
    private Double valeur;
    private String appreciation;
    private LocalDate dateSaisie;
    private Long etudiantId;
    private Long professeurId;
    private Long evaluationId;
}
