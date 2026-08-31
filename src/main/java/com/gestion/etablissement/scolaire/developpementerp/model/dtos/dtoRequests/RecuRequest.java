package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

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
public class RecuRequest {

    @NotBlank(message = "Le numéro de reçu est obligatoire")
    private String numeroRecu;

    @NotNull(message = "La date d'émission est obligatoire")
    private LocalDate dateEmission;

    @NotNull(message = "Le montant payé est obligatoire")
    private Double montantPaye;

    @NotNull(message = "L'ID du paiement est obligatoire")
    private Long paiementId;
}
