package com.gestion.etablissement.scolaire.developpementerp.presentation;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.NoteRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.NoteResponce;
import com.gestion.etablissement.scolaire.developpementerp.services.INoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-note")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class NoteController {

    private final INoteService noteService;

    @Operation(summary = "Cette opération permet d'ajouter une Note dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'opération d'ajout est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            })
    })
    @PostMapping("/add-Note")
    public ResponseEntity<NoteResponce> addNote(@RequestBody NoteRequest noteRequest) {
        log.debug("add note for etudiant ID: {}", noteRequest.getEtudiantId());
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.addNote(noteRequest));
    }

    @Operation(summary = "Cette opération permet de récupérer toutes les notes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            })
    })
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
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            })
    })
    @GetMapping("/getNoteById/{idNote}")
    public ResponseEntity<NoteResponce> getNoteById(@PathVariable("idNote") Long idNote) {
        log.debug("getNoteById CONTROLLER - ID: {}", idNote);
        return ResponseEntity.ok(noteService.getNoteById(idNote));
    }

    @Operation(summary = "Cette opération permet de modifier une Note dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'opération est effectuée avec succès", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            })
    })
    @PatchMapping("/update-Note/{idNote}")
    public ResponseEntity<NoteResponce> updateNote(@PathVariable("idNote") Long idNote,
                                                    @RequestBody NoteRequest noteRequest) {
        log.debug("update Note ID: {}", idNote);
        return ResponseEntity.ok(noteService.updateNote(idNote, noteRequest));
    }

    @Operation(summary = "Cette opération permet de supprimer une Note dans la base.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "L'opération est effectuée avec succès"),
            @ApiResponse(responseCode = "400", description = "La requête envoyée est incorrecte. Bad Request !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "401", description = "L'utilisateur n'est pas authentifié", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "403", description = "L'utilisateur n'est pas autorisé à faire l'action demandée", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "404", description = "La ressource demandée est introuvable. Not Found !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erreur Server !", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteResponce.class))
            })
    })
    @DeleteMapping("/delete-Note/{idNote}")
    public ResponseEntity<Void> deleteNote(@PathVariable("idNote") Long idNote) {
        log.debug("Delete Note : {}", idNote);
        noteService.deleteNote(idNote);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calculer la moyenne d'un étudiant pour une matière donnée.")
    @GetMapping("/moyenne-matiere/{etudiantId}/{matiereId}")
    public ResponseEntity<Double> calculateMoyenneMatiere(@PathVariable("etudiantId") Long etudiantId,
                                                          @PathVariable("matiereId") Long matiereId) {
        log.debug("calculateMoyenneMatiere - Etudiant: {}, Matiere: {}", etudiantId, matiereId);
        return ResponseEntity.ok(noteService.calculateMoyenneMatiere(etudiantId, matiereId));
    }

    @Operation(summary = "Calculer la moyenne générale d'un étudiant (pondérée par coefficients).")
    @GetMapping("/moyenne-generale/{etudiantId}")
    public ResponseEntity<Double> calculateMoyenneGenerale(@PathVariable("etudiantId") Long etudiantId) {
        log.debug("calculateMoyenneGenerale - Etudiant: {}", etudiantId);
        return ResponseEntity.ok(noteService.calculateMoyenneGenerale(etudiantId));
    }
    @GetMapping("/mes-notes")
    @PreAuthorize("hasRole('ETUDIANT')")
    public ResponseEntity<List<NoteResponce>> getMesNotes(Authentication authentication) {
        // authentication.getName() kat-rje3 l-email dyal l-etudiant li m-connecter
        String emailEtudiant = authentication.getName();
        List<NoteResponce> mesNotes = noteService.getNotesByEtudiantEmail(emailEtudiant);
        return ResponseEntity.ok(mesNotes);
    }
}
