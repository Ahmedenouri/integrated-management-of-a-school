package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Evaluation;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Professeur;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.INoteMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EvaluationRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.NoteRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ProfesseurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.INoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NoteServiceImpl implements INoteService {

    private final INoteMapper noteMapper;
    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final EvaluationRepository evaluationRepository;

    @Override
    public NoteResponce addNote(NoteRequest noteRequest) {
        log.debug("Adding note for etudiant ID: {}", noteRequest.getEtudiantId());

        Note note = noteMapper.map(noteRequest);
        assignRelations(note, noteRequest);

        Note savedNote = noteRepository.save(note);
        return noteMapper.map(savedNote);
    }

    @Override
    public NoteResponce updateNote(Long idNote, NoteRequest noteRequest) {
        log.debug("Updating note ID: {}", idNote);

        Note existingNote = findNoteOrThrow(idNote);
        noteMapper.updateFromRequest(noteRequest, existingNote);
        assignRelations(existingNote, noteRequest);

        Note savedNote = noteRepository.save(existingNote);
        return noteMapper.map(savedNote);
    }

    @Override
    public void deleteNote(Long idNote) {
        log.debug("Deleting note ID: {}", idNote);

        if (!noteRepository.existsById(idNote)) {
            throw new ResourceNotFoundException("Note not found with ID: " + idNote);
        }

        noteRepository.deleteById(idNote);
    }

    @Override
    public List<NoteResponce> getAllNotes() {
        log.debug("Fetching all notes");
        return noteMapper.mapList(noteRepository.findAll());
    }

    @Override
    public NoteResponce getNoteById(Long idNote) {
        log.debug("Fetching note ID: {}", idNote);
        return noteMapper.map(findNoteOrThrow(idNote));
    }

    private Note findNoteOrThrow(Long idNote) {
        return noteRepository.findById(idNote)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with ID: " + idNote));
    }

    private void assignRelations(Note note, NoteRequest noteRequest) {
        if (noteRequest.getEtudiantId() != null) {
            Etudiant etudiant = etudiantRepository.findById(noteRequest.getEtudiantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + noteRequest.getEtudiantId()));
            note.setEtudiant(etudiant);
        }

        if (noteRequest.getProfesseurId() != null) {
            Professeur professeur = professeurRepository.findById(noteRequest.getProfesseurId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professeur not found with ID: " + noteRequest.getProfesseurId()));
            note.setProfesseur(professeur);
        }

        if (noteRequest.getEvaluationId() != null) {
            Evaluation evaluation = evaluationRepository.findById(noteRequest.getEvaluationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluation not found with ID: " + noteRequest.getEvaluationId()));
            note.setEvaluation(evaluation);
        }
    }
}
