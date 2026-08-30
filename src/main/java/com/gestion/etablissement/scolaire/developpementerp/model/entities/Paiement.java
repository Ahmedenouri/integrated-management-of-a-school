package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.ModePaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.StatutPaiement;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypePaiement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referencePaiement;

    @Enumerated(EnumType.STRING)
    private TypePaiement typePaiement;

    private Double montant;
    private LocalDate datePaiement;

    @Enumerated(EnumType.STRING)
    private ModePaiement mode;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "responsable_financier_id")
    private ResponsableFinancier responsableFinancier;

    @OneToOne(mappedBy = "paiement", cascade = CascadeType.ALL)
    private Recu recu;

}