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
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valeur;
    private String appreciation;
    private LocalDate dateSaisie;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

}