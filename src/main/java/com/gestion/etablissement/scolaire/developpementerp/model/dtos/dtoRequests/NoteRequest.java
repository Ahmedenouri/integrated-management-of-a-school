package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoteRequest {

    private Double valeur;
    private String appreciation;
    private LocalDate dateSaisie;
    private Long etudiantId;
    private Long professeurId;
    private Long evaluationId;
}
