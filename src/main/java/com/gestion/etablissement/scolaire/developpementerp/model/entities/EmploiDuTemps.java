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
public class EmploiDuTemps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer semestre;
    private Boolean estValide = false;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "surveillant_id")
    private Surveillant surveillant;

    @OneToMany(mappedBy = "emploiDuTemps", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seance> seances = new ArrayList<>();

}