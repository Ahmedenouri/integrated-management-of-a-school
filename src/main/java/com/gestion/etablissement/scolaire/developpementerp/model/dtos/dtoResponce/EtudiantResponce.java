package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EtudiantResponce extends UtilisateurResponce {

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    private Role role;

    private Boolean estActif = true;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private String cne;
    private LocalDate dateNaissance;
    private String nomParent;
    private String telephoneParent;
    private String emailParent;
    private Long classeId;
}
