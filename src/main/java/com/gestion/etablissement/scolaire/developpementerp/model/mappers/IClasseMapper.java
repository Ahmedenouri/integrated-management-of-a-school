package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ClasseRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ClasseResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Classe;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IClasseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiants", ignore = true)
    @Mapping(target = "emplois", ignore = true)
    Classe map(ClasseRequest classeRequest);

    ClasseResponce map(Classe classe);

    List<ClasseResponce> mapList(List<Classe> classes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiants", ignore = true)
    @Mapping(target = "emplois", ignore = true)
    void updateFromRequest(ClasseRequest classeRequest, @MappingTarget Classe classe);
}
