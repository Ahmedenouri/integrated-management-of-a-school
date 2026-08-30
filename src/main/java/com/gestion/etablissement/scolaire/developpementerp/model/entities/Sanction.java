package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.TypeSanction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEmission;

    @Enumerated(EnumType.STRING)
    private TypeSanction type;

    private String motif;
    private Integer totalAbsencesAuMoment;
    private Boolean estTraitee;

    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "surveillant_id")
    private Surveillant surveillant;

}