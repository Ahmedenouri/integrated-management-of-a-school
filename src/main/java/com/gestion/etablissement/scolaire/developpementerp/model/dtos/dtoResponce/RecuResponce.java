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
public class RecuResponce {

    private Long id;
    private String numeroRecu;
    private LocalDate dateEmission;
    private Double montantPaye;
    private Long paiementId;
}
