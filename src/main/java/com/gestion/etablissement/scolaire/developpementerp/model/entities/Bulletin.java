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
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String anneeScolaire;
    private Integer semestre;
    private Double moyenneGenerale;
    private LocalDate dateGeneration;
    private String appreciationGenerale;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "directeur_id")
    private Directeur directeur;

}