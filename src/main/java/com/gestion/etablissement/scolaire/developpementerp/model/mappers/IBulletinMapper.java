package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.BulletinRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.BulletinResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Bulletin;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBulletinMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "directeur", ignore = true)
    Bulletin map(BulletinRequest bulletinRequest);

    @Mapping(target = "etudiantId", source = "etudiant.id")
    @Mapping(target = "directeurId", source = "directeur.id")
    BulletinResponce map(Bulletin bulletin);

    List<BulletinResponce> mapList(List<Bulletin> bulletins);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "directeur", ignore = true)
    void updateFromRequest(BulletinRequest bulletinRequest, @MappingTarget Bulletin bulletin);
}
