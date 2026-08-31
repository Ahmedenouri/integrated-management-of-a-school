package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.PaiementRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.PaiementResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Paiement;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPaiementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "responsableFinancier", ignore = true)
    @Mapping(target = "recu", ignore = true)
    Paiement map(PaiementRequest paiementRequest);

    @Mapping(target = "etudiantId", source = "etudiant.id")
    @Mapping(target = "responsableFinancierId", source = "responsableFinancier.id")
    PaiementResponce map(Paiement paiement);

    List<PaiementResponce> mapList(List<Paiement> paiements);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "responsableFinancier", ignore = true)
    @Mapping(target = "recu", ignore = true)
    void updateFromRequest(PaiementRequest paiementRequest, @MappingTarget Paiement paiement);
}
