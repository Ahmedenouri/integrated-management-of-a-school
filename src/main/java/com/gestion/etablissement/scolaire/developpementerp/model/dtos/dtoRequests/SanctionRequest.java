package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeSanction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SanctionRequest {

    @NotNull(message = "La date d'émission est obligatoire")
    private LocalDate dateEmission;

    @NotNull(message = "Le type de sanction est obligatoire")
    private TypeSanction type;

    @NotBlank(message = "Le motif est obligatoire")
    private String motif;

    private Integer totalAbsencesAuMoment;
    private Boolean estTraitee = false;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private Long etudiantId;

    private Long surveillantId;
}
