package com.gestion.etablissement.scolaire.developpementerp.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Setter
@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class Professeur extends Utilisateur {

    private String specialite;

}
