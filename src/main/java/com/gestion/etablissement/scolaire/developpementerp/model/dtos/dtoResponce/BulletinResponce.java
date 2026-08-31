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
public class BulletinResponce {

    private Long id;
    private String anneeScolaire;
    private Integer semestre;
    private Double moyenneGenerale;
    private LocalDate dateGeneration;
    private String appreciationGenerale;
    private Long etudiantId;
    private Long directeurId;
}
