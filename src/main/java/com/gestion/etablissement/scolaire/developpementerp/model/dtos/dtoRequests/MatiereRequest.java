package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatiereRequest {

    @NotBlank(message = "Le code de la matière est obligatoire")
    private String code;

    @NotBlank(message = "L'intitulé de la matière est obligatoire")
    private String intitule;

    @NotNull(message = "Le coefficient est obligatoire")
    private Double coefficient;

    @NotNull(message = "Le volume horaire est obligatoire")
    private Integer volumeHoraire;
}
