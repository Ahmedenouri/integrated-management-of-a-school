package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.AbsenceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.AbsenceResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Absence;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAbsenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seance", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    Absence map(AbsenceRequest absenceRequest);

    @Mapping(target = "seanceId", source = "seance.id")
    @Mapping(target = "etudiantId", source = "etudiant.id")
    @Mapping(target = "surveillantId", source = "surveillant.id")
    AbsenceResponce map(Absence absence);

    List<AbsenceResponce> mapList(List<Absence> absences);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seance", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    void updateFromRequest(AbsenceRequest absenceRequest, @MappingTarget Absence absence);
}
