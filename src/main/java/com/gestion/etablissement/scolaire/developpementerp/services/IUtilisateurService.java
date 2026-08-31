package com.gestion.etablissement.scolaire.developpementerp.services;


import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.UtilisateurResponce;

import java.util.List;

public interface IUtilisateurService {
    List<UtilisateurResponce>getAllUsers();
    UtilisateurResponce getUserById(Long iduser);
}
