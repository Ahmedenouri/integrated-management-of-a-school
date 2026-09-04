package com.gestion.etablissement.scolaire.developpementerp.security;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.EvaluationRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.*;
import com.gestion.etablissement.scolaire.developpementerp.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("securityService")
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SecurityEvaluationService {

    private final UtilisateurRepository utilisateurRepository;
    private final EtudiantRepository etudiantRepository;
    private final NoteRepository noteRepository;
    private final PaiementRepository paiementRepository;
    private final RecuRepository recuRepository;
    private final AbsenceRepository absenceRepository;
    private final SanctionRepository sanctionRepository;
    private final EvaluationRepository evaluationRepository;
    private final SeanceRepository seanceRepository;

    public String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        return auth.getName();
    }

    public boolean isCurrentUser(Long userId) {
        if (userId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return utilisateurRepository.findById(userId)
                .map(u -> email.equalsIgnoreCase(u.getEmail()))
                .orElse(false);
    }

    public boolean isEtudiantSelf(Long etudiantId) {
        if (etudiantId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return etudiantRepository.findById(etudiantId)
                .map(e -> email.equalsIgnoreCase(e.getEmail()))
                .orElse(false);
    }

    public boolean isPaiementOwner(Long paiementId) {
        if (paiementId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return paiementRepository.findById(paiementId)
                .map(p -> p.getEtudiant() != null && email.equalsIgnoreCase(p.getEtudiant().getEmail()))
                .orElse(false);
    }

    public boolean isRecuOwner(Long recuId) {
        if (recuId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return recuRepository.findById(recuId)
                .map(r -> r.getPaiement() != null && r.getPaiement().getEtudiant() != null
                        && email.equalsIgnoreCase(r.getPaiement().getEtudiant().getEmail()))
                .orElse(false);
    }

    public boolean isAbsenceOwner(Long absenceId) {
        if (absenceId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return absenceRepository.findById(absenceId)
                .map(a -> a.getEtudiant() != null && email.equalsIgnoreCase(a.getEtudiant().getEmail()))
                .orElse(false);
    }

    public boolean isSanctionOwner(Long sanctionId) {
        if (sanctionId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        return sanctionRepository.findById(sanctionId)
                .map(s -> s.getEtudiant() != null && email.equalsIgnoreCase(s.getEtudiant().getEmail()))
                .orElse(false);
    }

    public boolean canProfesseurGrade(NoteRequest noteRequest) {
        if (noteRequest == null) return false;
        String profEmail = getCurrentUserEmail();
        if (profEmail == null) return false;

        if (noteRequest.getEvaluationId() == null) {
            log.warn("Evaluation ID manquant dans NoteRequest lors du contrôle de sécurité");
            return false;
        }

        Evaluation evaluation = evaluationRepository.findById(noteRequest.getEvaluationId()).orElse(null);
        if (evaluation == null || evaluation.getMatiere() == null) {
            log.warn("Évaluation ou matière introuvable pour l'ID: {}", noteRequest.getEvaluationId());
            return false;
        }

        boolean teachesMatiere = seanceRepository.existsByProfesseurEmailAndMatiereId(profEmail, evaluation.getMatiere().getId());
        if (!teachesMatiere) {
            log.warn("Le professeur '{}' n'enseigne pas la matière '{}' de l'évaluation {}",
                    profEmail, evaluation.getMatiere().getIntitule(), evaluation.getId());
            return false;
        }

        if (noteRequest.getEtudiantId() != null) {
            boolean isStudentInProfClass = etudiantRepository.isProfesseurOfEtudiant(profEmail, noteRequest.getEtudiantId());
            if (!isStudentInProfClass) {
                log.warn("L'étudiant {} ne fait pas partie des classes enseignées par '{}'", noteRequest.getEtudiantId(), profEmail);
                return false;
            }
        }

        return true;
    }

    public boolean isProfesseurNoteOwner(Long noteId) {
        if (noteId == null) return false;
        String profEmail = getCurrentUserEmail();
        if (profEmail == null) return false;

        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) return false;

        if (note.getProfesseur() != null && profEmail.equalsIgnoreCase(note.getProfesseur().getEmail())) {
            return true;
        }

        if (note.getEvaluation() != null && note.getEvaluation().getMatiere() != null) {
            return seanceRepository.existsByProfesseurEmailAndMatiereId(profEmail, note.getEvaluation().getMatiere().getId());
        }

        return false;
    }

    public boolean isNoteViewer(Long noteId) {
        if (noteId == null) return false;
        String email = getCurrentUserEmail();
        if (email == null) return false;

        Note note = noteRepository.findById(noteId).orElse(null);
        if (note == null) return false;

        if (note.getEtudiant() != null && email.equalsIgnoreCase(note.getEtudiant().getEmail())) {
            return true;
        }

        if (note.getProfesseur() != null && email.equalsIgnoreCase(note.getProfesseur().getEmail())) {
            return true;
        }

        if (note.getEvaluation() != null && note.getEvaluation().getMatiere() != null) {
            return seanceRepository.existsByProfesseurEmailAndMatiereId(email, note.getEvaluation().getMatiere().getId());
        }

        return false;
    }

    public boolean isProfesseurOfEtudiant(Long etudiantId) {
        if (etudiantId == null) return false;
        String profEmail = getCurrentUserEmail();
        if (profEmail == null) return false;

        return etudiantRepository.isProfesseurOfEtudiant(profEmail, etudiantId);
    }

    public boolean canManageEvaluation(Long evaluationId, EvaluationRequest request) {
        String profEmail = getCurrentUserEmail();
        if (profEmail == null) return false;

        if (evaluationId != null) {
            Evaluation evaluation = evaluationRepository.findById(evaluationId).orElse(null);
            if (evaluation == null || evaluation.getMatiere() == null) return false;
            return seanceRepository.existsByProfesseurEmailAndMatiereId(profEmail, evaluation.getMatiere().getId());
        }

        if (request != null && request.getMatiereId() != null) {
            return seanceRepository.existsByProfesseurEmailAndMatiereId(profEmail, request.getMatiereId());
        }

        return false;
    }
}
