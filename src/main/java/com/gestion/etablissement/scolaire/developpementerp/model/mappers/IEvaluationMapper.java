package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EvaluationRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EvaluationResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Evaluation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEvaluationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "matiere", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Evaluation map(EvaluationRequest evaluationRequest);

    @Mapping(target = "matiereId", source = "matiere.id")
    EvaluationResponce map(Evaluation evaluation);

    List<EvaluationResponce> mapList(List<Evaluation> evaluations);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "matiere", ignore = true)
    @Mapping(target = "notes", ignore = true)
    void updateFromRequest(EvaluationRequest evaluationRequest, @MappingTarget Evaluation evaluation);
}
