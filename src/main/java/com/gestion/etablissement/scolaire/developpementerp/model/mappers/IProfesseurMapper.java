package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ProfesseurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ProfesseurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Professeur;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProfesseurMapper {
    Professeur map(ProfesseurRequest professeurRequest);

    ProfesseurResponce map(Professeur professeur);

    List<ProfesseurResponce> mapList(List<Professeur> professeurs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(ProfesseurRequest professeurRequest, @MappingTarget Professeur professeur);
}
