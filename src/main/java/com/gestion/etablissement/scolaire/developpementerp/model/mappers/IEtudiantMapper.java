package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EtudiantRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.EtudiantResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IEtudiantMapper {

    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    @Mapping(target = "bulletins", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "absences", ignore = true)
    Etudiant map(EtudiantRequest etudiantRequest);

    @Mapping(target = "classeId", source = "classe.id")
    EtudiantResponce map(Etudiant etudiant);

    List<EtudiantResponce> mapList(List<Etudiant> etudiants);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "classe", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    @Mapping(target = "bulletins", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "absences", ignore = true)
    void updateFromRequest(EtudiantRequest etudiantRequest, @MappingTarget Etudiant etudiant);
}
