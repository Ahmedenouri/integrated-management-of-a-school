package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClasseRequest {

    private String nom;
    private String niveau;
    private String anneeScolaire;
}
