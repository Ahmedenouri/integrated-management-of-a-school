package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.ResponsableFinancierRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.ResponsableFinancierResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.ResponsableFinancier;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IResponsableFinancierMapper {
    ResponsableFinancier map(ResponsableFinancierRequest responsableFinancierRequest);

    ResponsableFinancierResponce map(ResponsableFinancier responsableFinancier);

    List<ResponsableFinancierResponce> mapList(List<ResponsableFinancier> responsableFinanciers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(ResponsableFinancierRequest responsableFinancierRequest, @MappingTarget ResponsableFinancier responsableFinancier);
}
