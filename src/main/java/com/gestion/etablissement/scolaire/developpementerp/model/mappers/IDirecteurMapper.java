package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.DirecteurRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DirecteurResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Directeur;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDirecteurMapper {
    Directeur map(DirecteurRequest directeurRequest);

    DirecteurResponce map(Directeur directeur);

    List<DirecteurResponce> mapList(List<Directeur> directeurs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(DirecteurRequest directeurRequest, @MappingTarget Directeur directeur);
}
