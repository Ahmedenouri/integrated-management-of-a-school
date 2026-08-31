package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SurveillantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SurveillantResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Surveillant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISurveillantMapper {
    Surveillant map(SurveillantRequest surveillantRequest);

    SurveillantResponce map(Surveillant surveillant);

    List<SurveillantResponce> mapList(List<Surveillant> surveillants);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(SurveillantRequest surveillantRequest, @MappingTarget Surveillant surveillant);
}
