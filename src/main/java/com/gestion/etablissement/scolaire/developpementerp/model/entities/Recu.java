package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroRecu;
    private LocalDate dateEmission;
    private Double montantPaye;

    @OneToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;

}