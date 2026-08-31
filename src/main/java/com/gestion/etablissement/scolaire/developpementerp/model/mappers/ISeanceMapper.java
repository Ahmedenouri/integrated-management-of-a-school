package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.SeanceRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.SeanceResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Seance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISeanceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emploiDuTemps", ignore = true)
    @Mapping(target = "professeur", ignore = true)
    @Mapping(target = "salle", ignore = true)
    @Mapping(target = "matiere", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    Seance map(SeanceRequest seanceRequest);

    @Mapping(target = "emploiDuTempsId", source = "emploiDuTemps.id")
    @Mapping(target = "professeurId", source = "professeur.id")
    @Mapping(target = "salleId", source = "salle.id")
    @Mapping(target = "matiereId", source = "matiere.id")
    @Mapping(target = "surveillantId", source = "surveillant.id")
    SeanceResponce map(Seance seance);

    List<SeanceResponce> mapList(List<Seance> seances);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emploiDuTemps", ignore = true)
    @Mapping(target = "professeur", ignore = true)
    @Mapping(target = "salle", ignore = true)
    @Mapping(target = "matiere", ignore = true)
    @Mapping(target = "surveillant", ignore = true)
    @Mapping(target = "absences", ignore = true)
    void updateFromRequest(SeanceRequest seanceRequest, @MappingTarget Seance seance);
}
