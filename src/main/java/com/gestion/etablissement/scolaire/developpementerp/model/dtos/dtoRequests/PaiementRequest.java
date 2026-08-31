package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.ModePaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.StatutPaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypePaiement;
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
public class PaiementRequest {

    @NotBlank(message = "La référence de paiement est obligatoire")
    private String referencePaiement;

    @NotNull(message = "Le type de paiement est obligatoire")
    private TypePaiement typePaiement;

    @NotNull(message = "Le montant est obligatoire")
    private Double montant;

    @NotNull(message = "La date de paiement est obligatoire")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private ModePaiement mode;

    @NotNull(message = "Le statut du paiement est obligatoire")
    private StatutPaiement statut;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private Long etudiantId;

    private Long responsableFinancierId;
}
