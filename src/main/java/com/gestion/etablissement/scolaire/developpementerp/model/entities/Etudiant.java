package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant extends Utilisateur {

    private String cne;

    private LocalDate dateNaissance;

    private String nomParent;
    private String telephoneParent;
    private String emailParent;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Paiement> paiements = new ArrayList<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Bulletin> bulletins = new ArrayList<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Absence> absences = new ArrayList<>();

}