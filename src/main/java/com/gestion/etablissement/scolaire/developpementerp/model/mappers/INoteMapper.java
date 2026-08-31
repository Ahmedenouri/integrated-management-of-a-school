package com.gestion.etablissement.scolaire.developpementerp.model.mappers;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface INoteMapper {

    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "professeur", ignore = true)
    @Mapping(target = "evaluation", ignore = true)
    Note map(NoteRequest noteRequest);

    @Mapping(target = "etudiantId", source = "etudiant.id")
    @Mapping(target = "professeurId", source = "professeur.id")
    @Mapping(target = "evaluationId", source = "evaluation.id")
    NoteResponce map(Note note);

    List<NoteResponce> mapList(List<Note> notes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "etudiant", ignore = true)
    @Mapping(target = "professeur", ignore = true)
    @Mapping(target = "evaluation", ignore = true)
    void updateFromRequest(NoteRequest noteRequest, @MappingTarget Note note);
}
