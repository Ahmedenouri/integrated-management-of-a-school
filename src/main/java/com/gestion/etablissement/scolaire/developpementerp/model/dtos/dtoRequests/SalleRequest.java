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
public class SalleRequest {

    @NotBlank(message = "Le code de la salle est obligatoire")
    private String codeSalle;

    @NotNull(message = "La capacité est obligatoire")
    private Integer capacite;

    @NotBlank(message = "Le type de salle est obligatoire")
    private String typeSalle;
}
