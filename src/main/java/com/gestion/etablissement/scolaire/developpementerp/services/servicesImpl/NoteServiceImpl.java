package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Evaluation;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Matiere;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Professeur;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.BusinessException;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.INoteMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EvaluationRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.MatiereRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.NoteRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.ProfesseurRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.INoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class NoteServiceImpl implements INoteService {

    private final INoteMapper noteMapper;
    private final NoteRepository noteRepository;
    private final EtudiantRepository etudiantRepository;
    private final ProfesseurRepository professeurRepository;
    private final EvaluationRepository evaluationRepository;
    private final MatiereRepository matiereRepository;

    @Override
    public NoteResponce addNote(NoteRequest noteRequest) {
        log.debug("Adding note for etudiant ID: {}", noteRequest.getEtudiantId());

        if (noteRequest.getProfesseurId() == null) {
            throw new BusinessException("Habilitation refusée : Un professeur référent ou le Directeur doit être associé à la saisie de note (RG-PED-02).");
        }

        Note note = noteMapper.map(noteRequest);
        assignRelations(note, noteRequest);

        Note savedNote = noteRepository.save(note);
        return noteMapper.map(savedNote);
    }

    @Override
    public NoteResponce updateNote(Long idNote, NoteRequest noteRequest) {
        log.debug("Updating note ID: {}", idNote);

        if (noteRequest.getProfesseurId() == null) {
            throw new BusinessException("Habilitation refusée : Un professeur référent ou le Directeur doit être associé à la modification de note (RG-PED-02).");
        }

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
    @Transactional(readOnly = true)
    public List<NoteResponce> getAllNotes() {
        log.debug("Fetching all notes");
        return noteMapper.mapList(noteRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public NoteResponce getNoteById(Long idNote) {
        log.debug("Fetching note ID: {}", idNote);
        return noteMapper.map(findNoteOrThrow(idNote));
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateMoyenneMatiere(Long etudiantId, Long matiereId) {
        log.debug("Calculating average for etudiant ID {} in matiere ID {}", etudiantId, matiereId);
        List<Note> notes = noteRepository.findByEtudiantIdAndEvaluationMatiereId(etudiantId, matiereId);
        if (notes.isEmpty()) {
            return 0.0;
        }

        double totalWeightedPoints = 0.0;
        double totalCoefficients = 0.0;

        for (Note note : notes) {
            double noteVal = note.getValeur() != null ? note.getValeur() : 0.0;
            double coeff = (note.getEvaluation() != null && note.getEvaluation().getCoefficient() != null)
                    ? note.getEvaluation().getCoefficient()
                    : 1.0;

            totalWeightedPoints += noteVal * coeff;
            totalCoefficients += coeff;
        }

        if (totalCoefficients == 0.0) return 0.0;
        return Math.round((totalWeightedPoints / totalCoefficients) * 100.0) / 100.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateMoyenneGenerale(Long etudiantId) {
        log.debug("Calculating overall GPA for etudiant ID {}", etudiantId);
        List<Note> allNotes = noteRepository.findByEtudiantId(etudiantId);
        if (allNotes.isEmpty()) {
            return 0.0;
        }

        Map<Long, Double> subjectWeightedSum = new HashMap<>();
        Map<Long, Double> subjectCoeffSum = new HashMap<>();

        for (Note note : allNotes) {
            if (note.getEvaluation() == null || note.getEvaluation().getMatiere() == null) continue;

            Matiere matiere = note.getEvaluation().getMatiere();
            Long matId = matiere.getId();
            double noteVal = note.getValeur() != null ? note.getValeur() : 0.0;
            double evalCoeff = note.getEvaluation().getCoefficient() != null ? note.getEvaluation().getCoefficient() : 1.0;

            subjectWeightedSum.put(matId, subjectWeightedSum.getOrDefault(matId, 0.0) + (noteVal * evalCoeff));
            subjectCoeffSum.put(matId, subjectCoeffSum.getOrDefault(matId, 0.0) + evalCoeff);
        }

        if (subjectWeightedSum.isEmpty()) return 0.0;

        double totalSubjectWeightedAverage = 0.0;
        double totalSubjectCoefficients = 0.0;

        for (Long matId : subjectWeightedSum.keySet()) {
            Matiere matiere = matiereRepository.findById(matId).orElse(null);
            double matCoeff = (matiere != null && matiere.getCoefficient() != null) ? matiere.getCoefficient() : 1.0;

            double matAverage = subjectWeightedSum.get(matId) / subjectCoeffSum.get(matId);

            totalSubjectWeightedAverage += matAverage * matCoeff;
            totalSubjectCoefficients += matCoeff;
        }

        if (totalSubjectCoefficients == 0.0) return 0.0;
        return Math.round((totalSubjectWeightedAverage / totalSubjectCoefficients) * 100.0) / 100.0;
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
