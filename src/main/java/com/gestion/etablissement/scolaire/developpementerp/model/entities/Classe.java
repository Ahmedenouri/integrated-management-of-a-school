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
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String niveau;
    private String anneeScolaire;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<Etudiant> etudiants = new ArrayList<>();

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<EmploiDuTemps> emplois = new ArrayList<>();

    public Integer getNombreEtudiants() {
        return etudiants != null ? etudiants.size() : 0;
    }

}