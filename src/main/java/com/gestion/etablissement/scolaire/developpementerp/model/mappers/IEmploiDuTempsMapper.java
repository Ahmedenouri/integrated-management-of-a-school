package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EmploiDuTempsRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EmploiDuTempsResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.EmploiDuTemps;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEmploiDuTempsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    @Mapping(target = "seances", ignore = true)
    EmploiDuTemps map(EmploiDuTempsRequest emploiDuTempsRequest);

    @Mapping(target = "classeId", source = "classe.id")
    @Mapping(target = "surveillantId", source = "surveillant.id")
    EmploiDuTempsResponce map(EmploiDuTemps emploiDuTemps);

    List<EmploiDuTempsResponce> mapList(List<EmploiDuTemps> emplois);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    @Mapping(target = "seances", ignore = true)
    void updateFromRequest(EmploiDuTempsRequest emploiDuTempsRequest, @MappingTarget EmploiDuTemps emploiDuTemps);
}
