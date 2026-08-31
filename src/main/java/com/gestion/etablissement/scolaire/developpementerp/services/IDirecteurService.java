package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.DirecteurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DirecteurResponce;

import java.util.List;

public interface IDirecteurService {
    DirecteurResponce addDirecteur(DirecteurRequest directeurRequest);
    DirecteurResponce updateDirecteur(Long idDirecteur,DirecteurRequest directeurRequest);
    void deleteDirecteur(Long idDirecteur);
    List<DirecteurResponce> getAllDirecteurs();
    DirecteurResponce getDirecteurById(Long idDirecteur);
}
