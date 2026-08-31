package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurResponce {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    private Role role;

    private Boolean estActif = true;

    private LocalDateTime dateCreation = LocalDateTime.now();
}
