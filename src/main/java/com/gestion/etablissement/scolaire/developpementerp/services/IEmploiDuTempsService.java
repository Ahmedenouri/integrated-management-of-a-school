package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EmploiDuTempsRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EmploiDuTempsResponce;

import java.util.List;

public interface IEmploiDuTempsService {
    EmploiDuTempsResponce addEmploiDuTemps(EmploiDuTempsRequest emploiDuTempsRequest);
    EmploiDuTempsResponce updateEmploiDuTemps(Long idEmploi, EmploiDuTempsRequest emploiDuTempsRequest);
    void deleteEmploiDuTemps(Long idEmploi);
    List<EmploiDuTempsResponce> getAllEmploisDuTemps();
    EmploiDuTempsResponce getEmploiDuTempsById(Long idEmploi);
}
