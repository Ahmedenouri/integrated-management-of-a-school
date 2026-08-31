package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EtudiantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EtudiantResponce;

import java.util.List;

public interface IEtudiantService {

    EtudiantResponce addEtudiant(EtudiantRequest etudiantRequest);

    EtudiantResponce updateEtudiant(Long idEtudiant, EtudiantRequest etudiantRequest);

    void deleteEtudiant(Long idEtudiant);

    List<EtudiantResponce> getAllEtudiants();

    EtudiantResponce getEtudiantById(Long idEtudiant);
}
