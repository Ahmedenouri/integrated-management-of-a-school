package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Role role;
    private Boolean estActif;
    private LocalDateTime dateCreation;

    // Champs spécifiques Étudiant
    private String cne;
    private LocalDate dateNaissance;
    private String nomParent;
    private String telephoneParent;
    private String emailParent;
    private Long classeId;
    private String classeNom;

    // Champs spécifiques Professeur
    private String specialite;
}
