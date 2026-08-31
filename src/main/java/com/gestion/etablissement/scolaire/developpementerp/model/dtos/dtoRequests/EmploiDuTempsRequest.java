package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmploiDuTempsRequest {

    @NotNull(message = "Le semestre est obligatoire")
    private Integer semestre;

    private Boolean estValide = false;

    @NotNull(message = "L'ID de la classe est obligatoire")
    private Long classeId;

    private Long surveillantId;
}
