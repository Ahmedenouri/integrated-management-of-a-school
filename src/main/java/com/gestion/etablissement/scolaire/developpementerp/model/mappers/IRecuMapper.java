package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.RecuRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.RecuResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Recu;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRecuMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paiement", ignore = true)
    Recu map(RecuRequest recuRequest);

    @Mapping(target = "paiementId", source = "paiement.id")
    RecuResponce map(Recu recu);

    List<RecuResponce> mapList(List<Recu> recus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paiement", ignore = true)
    void updateFromRequest(RecuRequest recuRequest, @MappingTarget Recu recu);
}
