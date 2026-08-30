package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.JourSemaine;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private JourSemaine jour;

    private LocalTime heureDebut;
    private LocalTime heureFin;

    @ManyToOne
    @JoinColumn(name = "emploi_id")
    private EmploiDuTemps emploiDuTemps;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "surveillant_id")
    private Surveillant surveillant;

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL)
    private List<Absence> absences = new ArrayList<>();

}