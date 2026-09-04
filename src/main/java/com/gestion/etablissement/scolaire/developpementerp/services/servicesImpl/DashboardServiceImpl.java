package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Sanction;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.StatutPaiement;
import com.gestion.etablissement.scolaire.developpementerp.repositories.*;
import com.gestion.etablissement.scolaire.developpementerp.services.IDashboardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class DashboardServiceImpl implements IDashboardService {

    private final EtudiantRepository etudiantRepository;
    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;
    private final NoteRepository noteRepository;
    private final AbsenceRepository absenceRepository;
    private final SanctionRepository sanctionRepository;
    private final PaiementRepository paiementRepository;

    @Override
    public DashboardResponce getDashboardStats() {
        log.debug("Calculating real-time Dashboard statistics");

        // 1. Pedagogical Stats
        Long totalEtudiants = etudiantRepository.count();
        Long totalClasses = classeRepository.count();
        Long totalMatieres = matiereRepository.count();

        List<Note> notes = noteRepository.findAll();
        double moyenneEtablissement = 0.0;
        if (!notes.isEmpty()) {
            double sumVal = notes.stream().filter(n -> n.getValeur() != null).mapToDouble(Note::getValeur).sum();
            long countVal = notes.stream().filter(n -> n.getValeur() != null).count();
            if (countVal > 0) {
                moyenneEtablissement = Math.round((sumVal / countVal) * 100.0) / 100.0;
            }
        }

        // 2. Disciplinary Stats
        Long totalAbsences = absenceRepository.count();
        Integer totalHeuresAbsences = absenceRepository.findAll().stream()
                .filter(a -> a.getNombreHeures() != null)
                .mapToInt(a -> a.getNombreHeures())
                .sum();

        List<Sanction> sanctions = sanctionRepository.findAll();
        Long totalSanctions = (long) sanctions.size();

        Map<String, Long> sanctionsParType = new HashMap<>();
        for (Sanction s : sanctions) {
            if (s.getType() != null) {
                String typeStr = s.getType().name();
                sanctionsParType.put(typeStr, sanctionsParType.getOrDefault(typeStr, 0L) + 1);
            }
        }

        // 3. Financial Stats
        Double totalEncaissement = paiementRepository.sumMontantByStatut(StatutPaiement.PAYE);
        Double totalImpayes = paiementRepository.sumMontantByStatutIn(List.of(StatutPaiement.EN_RETARD, StatutPaiement.PARTIEL));
        Long nbEtudiantsEnRetard = paiementRepository.countDistinctEtudiantByStatutIn(List.of(StatutPaiement.EN_RETARD, StatutPaiement.PARTIEL));

        return DashboardResponce.builder()
                .totalEtudiants(totalEtudiants)
                .totalClasses(totalClasses)
                .totalMatieres(totalMatieres)
                .moyenneEtablissement(moyenneEtablissement)
                .totalAbsences(totalAbsences)
                .totalHeuresAbsences(totalHeuresAbsences)
                .totalSanctions(totalSanctions)
                .sanctionsParType(sanctionsParType)
                .totalEncaissementPercu(totalEncaissement != null ? totalEncaissement : 0.0)
                .totalImpayes(totalImpayes != null ? totalImpayes : 0.0)
                .nombreEtudiantsEnRetard(nbEtudiantsEnRetard != null ? nbEtudiantsEnRetard : 0L)
                .build();
    }

    @Override
    public com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardFinancierResponse getDashboardFinancierStats() {
        log.debug("Calculating financial dashboard statistics");
        Long totalEtudiants = etudiantRepository.count();
        Long totalClasses = classeRepository.count();
        Double totalEncaissement = paiementRepository.sumMontantByStatut(StatutPaiement.PAYE);
        Double totalImpayes = paiementRepository.sumMontantByStatutIn(List.of(StatutPaiement.EN_RETARD, StatutPaiement.PARTIEL));
        Long nbEtudiantsEnRetard = paiementRepository.countDistinctEtudiantByStatutIn(List.of(StatutPaiement.EN_RETARD, StatutPaiement.PARTIEL));

        return com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardFinancierResponse.builder()
                .totalEtudiants(totalEtudiants)
                .totalClasses(totalClasses)
                .totalEncaissementPercu(totalEncaissement != null ? totalEncaissement : 0.0)
                .totalImpayes(totalImpayes != null ? totalImpayes : 0.0)
                .nombreEtudiantsEnRetard(nbEtudiantsEnRetard != null ? nbEtudiantsEnRetard : 0L)
                .build();
    }

    @Override
    public com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardDisciplineResponse getDashboardDisciplineStats() {
        log.debug("Calculating disciplinary dashboard statistics");
        Long totalEtudiants = etudiantRepository.count();
        Long totalClasses = classeRepository.count();
        Long totalMatieres = matiereRepository.count();

        List<Note> notes = noteRepository.findAll();
        double moyenneEtablissement = 0.0;
        if (!notes.isEmpty()) {
            double sumVal = notes.stream().filter(n -> n.getValeur() != null).mapToDouble(Note::getValeur).sum();
            long countVal = notes.stream().filter(n -> n.getValeur() != null).count();
            if (countVal > 0) {
                moyenneEtablissement = Math.round((sumVal / countVal) * 100.0) / 100.0;
            }
        }

        Long totalAbsences = absenceRepository.count();
        Integer totalHeuresAbsences = absenceRepository.findAll().stream()
                .filter(a -> a.getNombreHeures() != null)
                .mapToInt(a -> a.getNombreHeures())
                .sum();

        List<Sanction> sanctions = sanctionRepository.findAll();
        Long totalSanctions = (long) sanctions.size();

        Map<String, Long> sanctionsParType = new HashMap<>();
        for (Sanction s : sanctions) {
            if (s.getType() != null) {
                String typeStr = s.getType().name();
                sanctionsParType.put(typeStr, sanctionsParType.getOrDefault(typeStr, 0L) + 1);
            }
        }

        return com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.DashboardDisciplineResponse.builder()
                .totalEtudiants(totalEtudiants)
                .totalClasses(totalClasses)
                .totalMatieres(totalMatieres)
                .moyenneEtablissement(moyenneEtablissement)
                .totalAbsences(totalAbsences)
                .totalHeuresAbsences(totalHeuresAbsences)
                .totalSanctions(totalSanctions)
                .sanctionsParType(sanctionsParType)
                .build();
    }
}
