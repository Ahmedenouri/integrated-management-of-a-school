package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.ModePaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.StatutPaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaiementResponce {

    private Long id;
    private String referencePaiement;
    private TypePaiement typePaiement;
    private Double montant;
    private LocalDate datePaiement;
    private ModePaiement mode;
    private StatutPaiement statut;
    private Long etudiantId;
    private Long responsableFinancierId;
}
