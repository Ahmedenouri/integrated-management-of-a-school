package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ClasseRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ClasseResponce;

import java.util.List;

public interface IClasseService {

    ClasseResponce addClasse(ClasseRequest classeRequest);

    ClasseResponce updateClasse(Long idClasse, ClasseRequest classeRequest);

    void deleteClasse(Long idClasse);

    List<ClasseResponce> getAllClasses();

    ClasseResponce getClasseById(Long idClasse);
}
