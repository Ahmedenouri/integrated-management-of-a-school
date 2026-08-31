package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.MatiereRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.MatiereResponce;

import java.util.List;

public interface IMatiereService {
    MatiereResponce addMatiere(MatiereRequest matiereRequest);
    MatiereResponce updateMatiere(Long idMatiere, MatiereRequest matiereRequest);
    void deleteMatiere(Long idMatiere);
    List<MatiereResponce> getAllMatieres();
    MatiereResponce getMatiereById(Long idMatiere);
}
