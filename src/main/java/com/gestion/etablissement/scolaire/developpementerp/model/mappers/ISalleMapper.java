package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SalleRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SalleResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Salle;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISalleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seances", ignore = true)
    Salle map(SalleRequest salleRequest);

    SalleResponce map(Salle salle);

    List<SalleResponce> mapList(List<Salle> salles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seances", ignore = true)
    void updateFromRequest(SalleRequest salleRequest, @MappingTarget Salle salle);
}
