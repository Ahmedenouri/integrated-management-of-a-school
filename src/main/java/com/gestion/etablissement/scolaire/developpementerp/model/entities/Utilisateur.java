package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean estActif = true;

    private LocalDateTime dateCreation = LocalDateTime.now();

}
