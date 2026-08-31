package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SanctionRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SanctionResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Sanction;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISanctionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    Sanction map(SanctionRequest sanctionRequest);

    @Mapping(target = "etudiantId", source = "etudiant.id")
    @Mapping(target = "surveillantId", source = "surveillant.id")
    SanctionResponce map(Sanction sanction);

    List<SanctionResponce> mapList(List<Sanction> sanctions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    void updateFromRequest(SanctionRequest sanctionRequest, @MappingTarget Sanction sanction);
}
