package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.INoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api-note")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "basicAuth")
public class NoteController {

    private final INoteService noteService;

    @Operation(summary = "Cette opération permet d'ajouter une Note dans la base (Professeur pour sa matière ou Directeur).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !"),
            @ApiResponse(responseCode = "403", description = "Accès refusé - Matière ou classe non autorisée"),
            @ApiResponse(responseCode = "500", description = "Erreur Server !")
    })
    @PreAuthorize("hasRole('DIRECTEUR') or (hasRole('PROFESSEUR') and @securityService.canProfesseurGrade(#noteRequest))")
    @PostMapping("/add-Note")
    public ResponseEntity<NoteResponce> addNote(@RequestBody NoteRequest noteRequest) {
        log.debug("add note for etudiant ID: {}", noteRequest.getEtudiantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.addNote(noteRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les notes (Directeur et Surveillant uniquement).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT')")
    @GetMapping("/getAllNotes")
    public ResponseEntity<List<NoteResponce>> getAllNotes() {
        log.debug("getAllNotes CONTROLLER");
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @Operation(summary = "Cette opération permet de récupérer une note par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !")
    })
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT') or @securityService.isNoteViewer(#idNote)")
    @GetMapping("/getNoteById/{idNote}")
    public ResponseEntity<NoteResponce> getNoteById(@PathVariable("idNote") Long idNote) {
        log.debug("getNoteById CONTROLLER - ID: {}", idNote);
        return ResponseEntity.ok(noteService.getNoteById(idNote));
    }

    @Operation(summary = "Cette opération permet de modifier une Note dans la base (Professeur pour sa matière ou Directeur).")
    @PreAuthorize("hasRole('DIRECTEUR') or (hasRole('PROFESSEUR') and @securityService.isProfesseurNoteOwner(#idNote))")
    @PatchMapping("/update-Note/{idNote}")
    public ResponseEntity<NoteResponce> updateNote(@PathVariable("idNote") Long idNote,
                                                    @RequestBody NoteRequest noteRequest) {
        log.debug("update Note ID: {}", idNote);
        return ResponseEntity.ok(noteService.updateNote(idNote, noteRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer une Note dans la base (Professeur pour sa matière ou Directeur).")
    @PreAuthorize("hasRole('DIRECTEUR') or (hasRole('PROFESSEUR') and @securityService.isProfesseurNoteOwner(#idNote))")
    @DeleteMapping("/delete-Note/{idNote}")
    public ResponseEntity<Void> deleteNote(@PathVariable("idNote") Long idNote) {
        log.debug("Delete Note : {}", idNote);
        noteService.deleteNote(idNote);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calculer la moyenne d'un étudiant pour une matière donnée.")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT') or @securityService.isEtudiantSelf(#etudiantId) or @securityService.isProfesseurOfEtudiant(#etudiantId)")
    @GetMapping("/moyenne-matiere/{etudiantId}/{matiereId}")
    public ResponseEntity<Map<String, Object>> calculateMoyenneMatiere(@PathVariable("etudiantId") Long etudiantId,
                                                          @PathVariable("matiereId") Long matiereId) {
        log.debug("calculateMoyenneMatiere - Etudiant: {}, Matiere: {}", etudiantId, matiereId);
        Double moyenne = noteService.calculateMoyenneMatiere(etudiantId, matiereId);
        return ResponseEntity.ok(Map.of(
                "etudiantId", etudiantId,
                "matiereId", matiereId,
                "moyenne", moyenne
        ));
    }

    @Operation(summary = "Calculer la moyenne générale d'un étudiant (pondérée par coefficients).")
    @PreAuthorize("hasAnyRole('DIRECTEUR', 'SURVEILLANT') or @securityService.isEtudiantSelf(#etudiantId) or @securityService.isProfesseurOfEtudiant(#etudiantId)")
    @GetMapping("/moyenne-generale/{etudiantId}")
    public ResponseEntity<Map<String, Object>> calculateMoyenneGenerale(@PathVariable("etudiantId") Long etudiantId) {
        log.debug("calculateMoyenneGenerale - Etudiant: {}", etudiantId);
        Double moyenne = noteService.calculateMoyenneGenerale(etudiantId);
        return ResponseEntity.ok(Map.of(
                "etudiantId", etudiantId,
                "moyenneGenerale", moyenne
        ));
    }

    @Operation(summary = "Consulter ses propres notes (Espace Étudiant).")
    @GetMapping("/mes-notes")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<List<NoteResponce>> getMesNotes(Authentication authentication) {
        String emailEtudiant = authentication.getName();
        List<NoteResponce> mesNotes = noteService.getNotesByEtudiantEmail(emailEtudiant);
        return ResponseEntity.ok(mesNotes);
    }
}
