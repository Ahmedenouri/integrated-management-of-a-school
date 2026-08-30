package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String intitule;
    private Double coefficient;
    private Integer volumeHoraire;

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    private List<Evaluation> evaluations = new ArrayList<>();

    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    private List<Seance> seances = new ArrayList<>();

}