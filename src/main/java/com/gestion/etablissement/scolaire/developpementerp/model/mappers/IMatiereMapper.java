package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.MatiereRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.MatiereResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Matiere;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IMatiereMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "seances", ignore = true)
    Matiere map(MatiereRequest matiereRequest);

    MatiereResponce map(Matiere matiere);

    List<MatiereResponce> mapList(List<Matiere> matieres);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "evaluations", ignore = true)
    @Mapping(target = "seances", ignore = true)
    void updateFromRequest(MatiereRequest matiereRequest, @MappingTarget Matiere matiere);
}
