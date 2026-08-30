package com.gestion.etablissement.scolaire.developpementerp.services;


import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.UtilisateurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Utilisateur;

import java.util.List;

public interface IUtilisateurService {
    List<UtilisateurResponce>getAllUtilisateurs();


}
