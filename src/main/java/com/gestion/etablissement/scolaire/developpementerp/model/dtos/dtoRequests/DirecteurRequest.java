package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import com.gestion.etablissement.scolaire.developpementerp.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DirecteurRequest extends UtilisateurRequest {
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;

    private Role role;

    private Boolean estActif = true;

    private LocalDateTime dateCreation = LocalDateTime.now();
}
