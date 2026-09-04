package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ProfesseurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfesseurResponce;

import java.util.List;

public interface IProfesseurService {
    ProfesseurResponce addProfesseur(ProfesseurRequest professeurRequest);
    ProfesseurResponce updateProfesseur(Long idProfesseur, ProfesseurRequest professeurRequest);
    void deleteProfesseur(Long idProfesseur);
    List<ProfesseurResponce> getAllProfesseurs();
    ProfesseurResponce getProfesseurById(Long idProfesseur);
    List<com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ClasseResponce> getClassesForProfesseur(String email);
    List<com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EtudiantResponce> getEtudiantsForProfesseur(String email);
}
