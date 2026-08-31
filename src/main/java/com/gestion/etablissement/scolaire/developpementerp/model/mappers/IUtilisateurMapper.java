package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.UtilisateurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Utilisateur;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface IUtilisateurMapper {
    List<UtilisateurResponce> listToResponce(List<Utilisateur> utilisateurs);
    UtilisateurResponce MapToResponce(Utilisateur utilisateur);
}