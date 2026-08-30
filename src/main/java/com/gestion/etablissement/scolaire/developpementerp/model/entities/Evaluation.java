package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeEvaluation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Enumerated(EnumType.STRING)
    private TypeEvaluation typeEval;

    private LocalDate dateEvaluation;

    private Double coefficient;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

}