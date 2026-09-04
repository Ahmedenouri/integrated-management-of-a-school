package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.AbsenceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.AbsenceResponce;

import java.util.List;

public interface IAbsenceService {
    AbsenceResponce addAbsence(AbsenceRequest absenceRequest);
    AbsenceResponce updateAbsence(Long idAbsence, AbsenceRequest absenceRequest);
    void deleteAbsence(Long idAbsence);
    List<AbsenceResponce> getAllAbsences();
    AbsenceResponce getAbsenceById(Long idAbsence);
    List<AbsenceResponce> getAbsencesByEtudiantEmail(String email);
}
