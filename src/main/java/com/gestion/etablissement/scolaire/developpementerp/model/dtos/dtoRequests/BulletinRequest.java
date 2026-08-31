package com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BulletinRequest {

    @NotBlank(message = "L'année scolaire est obligatoire")
    private String anneeScolaire;

    @NotNull(message = "Le semestre est obligatoire")
    private Integer semestre;

    private Double moyenneGenerale;
    private LocalDate dateGeneration;
    private String appreciationGenerale;

    @NotNull(message = "L'ID de l'étudiant est obligatoire")
    private Long etudiantId;

    private Long directeurId;
}
