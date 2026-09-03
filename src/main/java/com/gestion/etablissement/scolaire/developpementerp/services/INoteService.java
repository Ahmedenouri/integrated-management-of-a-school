package com.gestion.etablissement.scolaire.developpementerp.services;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;

import java.util.List;

public interface INoteService {

    NoteResponce addNote(NoteRequest noteRequest);

    NoteResponce updateNote(Long idNote, NoteRequest noteRequest);

    void deleteNote(Long idNote);

    List<NoteResponce> getAllNotes();

    NoteResponce getNoteById(Long idNote);

    Double calculateMoyenneMatiere(Long etudiantId, Long matiereId);

    Double calculateMoyenneGenerale(Long etudiantId);

    List<NoteResponce> getNotesByEtudiantEmail(String emailEtudiant);
}
