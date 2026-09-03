package com.gestion.etablissement.scolaire.developpementerp;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.*;
import com.gestion.etablissement.scolaire.developpementerp.model.enums.*;
import com.gestion.etablissement.scolaire.developpementerp.repositories.*;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DevelopmentErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevelopmentErpApplication.class, args);
	}

    /**************************************************************************/

    @Bean
    CommandLineRunner initDatabase(
            PasswordEncoder passwordEncoder,
            DirecteurRepository directeurRepository,
            ResponsableFinancierRepository responsableFinancierRepository,
            SurveillantRepository surveillantRepository,
            ProfesseurRepository professeurRepository,
            EtudiantRepository etudiantRepository,
            ClasseRepository classeRepository,
            MatiereRepository matiereRepository,
            EvaluationRepository evaluationRepository,
            NoteRepository noteRepository,
            BulletinRepository bulletinRepository,
            SalleRepository salleRepository,
            EmploiDuTempsRepository emploiDuTempsRepository,
            SeanceRepository seanceRepository,
            AbsenceRepository absenceRepository,
            SanctionRepository sanctionRepository,
            PaiementRepository paiementRepository,
            RecuRepository recuRepository
    ) {
        return args -> {
            System.out.println("🚀 Initialisation des données de l'ERP Scolaire...");

            // ==========================================================
            // 1. UTILISATEURS (Directeur, Responsable Financier, Surveillant, Professeurs)
            // ==========================================================
            Directeur directeur = new Directeur();
            directeur.setNom("Ennouri");
            directeur.setPrenom("Ahmed");
            directeur.setEmail("ahmed.ennouri@school.ma");
            directeur.setMotDePasse(passwordEncoder.encode("pass123"));
            directeur.setTelephone("0600000001");
            directeur.setRole(Role.DIRECTEUR);
            directeur.setEstActif(true);
            directeur.setDateCreation(LocalDateTime.now());
            directeurRepository.save(directeur);

            ResponsableFinancier respFin = new ResponsableFinancier();
            respFin.setNom("Bennani");
            respFin.setPrenom("Karim");
            respFin.setEmail("karim.bennani@school.ma");
            respFin.setMotDePasse(passwordEncoder.encode("pass123"));
            respFin.setTelephone("0600000002");
            respFin.setRole(Role.RESPONSABLE_FINANCIER);
            respFin.setEstActif(true);
            respFin.setDateCreation(LocalDateTime.now());
            responsableFinancierRepository.save(respFin);

            Surveillant surveillant = new Surveillant();
            surveillant.setNom("Tazi");
            surveillant.setPrenom("Rachid");
            surveillant.setEmail("rachid.tazi@school.ma");
            surveillant.setMotDePasse(passwordEncoder.encode("pass123"));
            surveillant.setTelephone("0600000003");
            surveillant.setRole(Role.SURVEILLANT);
            surveillant.setEstActif(true);
            surveillant.setDateCreation(LocalDateTime.now());
            surveillantRepository.save(surveillant);

            Professeur profMath = new Professeur();
            profMath.setNom("Chraibi");
            profMath.setPrenom("Hassan");
            profMath.setEmail("hassan.chraibi@school.ma");
            profMath.setMotDePasse(passwordEncoder.encode("pass123"));
            profMath.setTelephone("0600000004");
            profMath.setRole(Role.PROFESSEUR);
            profMath.setSpecialite("Mathématiques");
            profMath.setEstActif(true);
            profMath.setDateCreation(LocalDateTime.now());

            Professeur profPhysique = new Professeur();
            profPhysique.setNom("El Fassi");
            profPhysique.setPrenom("Sanaa");
            profPhysique.setEmail("sanaa.elfassi@school.ma");
            profPhysique.setMotDePasse(passwordEncoder.encode("pass123"));
            profPhysique.setTelephone("0600000005");
            profPhysique.setRole(Role.PROFESSEUR);
            profPhysique.setSpecialite("Physique-Chimie");
            profPhysique.setEstActif(true);
            profPhysique.setDateCreation(LocalDateTime.now());

            professeurRepository.saveAll(List.of(profMath, profPhysique));

            // ==========================================================
            // 2. PEDAGOGIE (Classes, Matieres, Etudiants)
            // ==========================================================
            Classe classeBac = new Classe();
            classeBac.setNom("2ème BAC BIOF - 1");
            classeBac.setNiveau("2ème BAC");
            classeBac.setAnneeScolaire("2025-2026");

            Classe classeTronc = new Classe();
            classeTronc.setNom("TC Scientifique - 2");
            classeTronc.setNiveau("Tronc Commun");
            classeTronc.setAnneeScolaire("2025-2026");

            classeRepository.saveAll(List.of(classeBac, classeTronc));

            Matiere math = new Matiere();
            math.setCode("MATH2BAC");
            math.setIntitule("Mathématiques");
            math.setCoefficient(7.0);
            math.setVolumeHoraire(120);

            Matiere physique = new Matiere();
            physique.setCode("PHY2BAC");
            physique.setIntitule("Physique-Chimie");
            physique.setCoefficient(7.0);
            physique.setVolumeHoraire(100);

            matiereRepository.saveAll(List.of(math, physique));

            // Etudiants
            Etudiant etudiant1 = new Etudiant();
            etudiant1.setNom("Alami");
            etudiant1.setPrenom("Youssef");
            etudiant1.setEmail("youssef.alami@student.ma");
            etudiant1.setMotDePasse(passwordEncoder.encode("pass123"));
            etudiant1.setTelephone("0611111111");
            etudiant1.setRole(Role.ETUDIANT);
            etudiant1.setEstActif(true);
            etudiant1.setDateCreation(LocalDateTime.now());
            etudiant1.setCne("R135894123");
            etudiant1.setDateNaissance(LocalDate.of(2007, 3, 14));
            etudiant1.setNomParent("Omar Alami");
            etudiant1.setTelephoneParent("0622222222");
            etudiant1.setEmailParent("omar.alami@gmail.com");
            etudiant1.setClasse(classeBac);

            Etudiant etudiant2 = new Etudiant();
            etudiant2.setNom("Badr");
            etudiant2.setPrenom("Salma");
            etudiant2.setEmail("salma.badr@student.ma");
            etudiant2.setMotDePasse(passwordEncoder.encode("pass123"));
            etudiant2.setTelephone("0633333333");
            etudiant2.setRole(Role.ETUDIANT);
            etudiant2.setEstActif(true);
            etudiant2.setDateCreation(LocalDateTime.now());
            etudiant2.setCne("R130098765");
            etudiant2.setDateNaissance(LocalDate.of(2007, 7, 22));
            etudiant2.setNomParent("Khalid Badr");
            etudiant2.setTelephoneParent("0644444444");
            etudiant2.setEmailParent("khalid.badr@gmail.com");
            etudiant2.setClasse(classeBac);

            Etudiant etudiant3 = new Etudiant();
            etudiant3.setNom("Mansouri");
            etudiant3.setPrenom("Amine");
            etudiant3.setEmail("amine.mansouri@student.ma");
            etudiant3.setMotDePasse(passwordEncoder.encode("pass123"));
            etudiant3.setTelephone("0655555555");
            etudiant3.setRole(Role.ETUDIANT);
            etudiant3.setEstActif(true);
            etudiant3.setDateCreation(LocalDateTime.now());
            etudiant3.setCne("G149087123");
            etudiant3.setDateNaissance(LocalDate.of(2008, 11, 5));
            etudiant3.setNomParent("Nadia Mansouri");
            etudiant3.setTelephoneParent("0666666666");
            etudiant3.setEmailParent("nadia.mansouri@gmail.com");
            etudiant3.setClasse(classeTronc);

            etudiantRepository.saveAll(List.of(etudiant1, etudiant2, etudiant3));

            // ==========================================================
            // 3. EVALUATIONS & NOTES & BULLETINS
            // ==========================================================
            Evaluation evalMath = new Evaluation();
            evalMath.setTitre("Contrôle N°1 - Analyse");
            evalMath.setTypeEval(TypeEvaluation.CONTROLE_CONTINU);
            evalMath.setDateEvaluation(LocalDate.of(2026, 2, 10));
            evalMath.setCoefficient(2.0);
            evalMath.setMatiere(math);

            Evaluation evalPhysique = new Evaluation();
            evalPhysique.setTitre("TP Ondes Mécaniques");
            evalPhysique.setTypeEval(TypeEvaluation.TP);
            evalPhysique.setDateEvaluation(LocalDate.of(2026, 2, 15));
            evalPhysique.setCoefficient(1.0);
            evalPhysique.setMatiere(physique);

            evaluationRepository.saveAll(List.of(evalMath, evalPhysique));

            Note note1 = new Note();
            note1.setValeur(17.5);
            note1.setAppreciation("Très bon travail");
            note1.setDateSaisie(LocalDate.now());
            note1.setEtudiant(etudiant1);
            note1.setEvaluation(evalMath);
            note1.setProfesseur(profMath);

            Note note2 = new Note();
            note2.setValeur(14.0);
            note2.setAppreciation("Bon travail");
            note2.setDateSaisie(LocalDate.now());
            note2.setEtudiant(etudiant2);
            note2.setEvaluation(evalMath);
            note2.setProfesseur(profMath);

            Note note3 = new Note();
            note3.setValeur(16.0);
            note3.setAppreciation("Bien");
            note3.setDateSaisie(LocalDate.now());
            note3.setEtudiant(etudiant1);
            note3.setEvaluation(evalPhysique);
            note3.setProfesseur(profPhysique);

            noteRepository.saveAll(List.of(note1, note2, note3));

            Bulletin bulletin1 = new Bulletin();
            bulletin1.setAnneeScolaire("2025-2026");
            bulletin1.setSemestre(1);
            bulletin1.setMoyenneGenerale(16.75);
            bulletin1.setDateGeneration(LocalDate.now());
            bulletin1.setAppreciationGenerale("Excellents résultats, continuez ainsi.");
            bulletin1.setEtudiant(etudiant1);
            bulletin1.setDirecteur(directeur);

            bulletinRepository.save(bulletin1);

            // ==========================================================
            // 4. EMPLOI DU TEMPS & SEANCES & SALLES
            // ==========================================================
            Salle salle1 = new Salle();
            salle1.setCodeSalle("B12");
            salle1.setCapacite(35);
            salle1.setTypeSalle("Cours");

            Salle salleLabo = new Salle();
            salleLabo.setCodeSalle("LABO-02");
            salleLabo.setCapacite(24);
            salleLabo.setTypeSalle("Laboratoire");

            salleRepository.saveAll(List.of(salle1, salleLabo));

            EmploiDuTemps edtBac = new EmploiDuTemps();
            edtBac.setSemestre(1);
            edtBac.setEstValide(true);
            edtBac.setClasse(classeBac);
            edtBac.setSurveillant(surveillant);
            emploiDuTempsRepository.save(edtBac);

            Seance seance1 = new Seance();
            seance1.setJour(JourSemaine.LUNDI);
            seance1.setHeureDebut(LocalTime.of(8, 30));
            seance1.setHeureFin(LocalTime.of(10, 30));
            seance1.setEmploiDuTemps(edtBac);
            seance1.setMatiere(math);
            seance1.setProfesseur(profMath);
            seance1.setSalle(salle1);
            seance1.setSurveillant(surveillant);

            Seance seance2 = new Seance();
            seance2.setJour(JourSemaine.MARDI);
            seance2.setHeureDebut(LocalTime.of(10, 30));
            seance2.setHeureFin(LocalTime.of(12, 30));
            seance2.setEmploiDuTemps(edtBac);
            seance2.setMatiere(physique);
            seance2.setProfesseur(profPhysique);
            seance2.setSalle(salleLabo);
            seance2.setSurveillant(surveillant);

            seanceRepository.saveAll(List.of(seance1, seance2));

            // ==========================================================
            // 5. DISCIPLINE (Absences, Sanctions)
            // ==========================================================
            Absence absence1 = new Absence();
            absence1.setDateAbsence(LocalDate.of(2026, 2, 20));
            absence1.setEstJustifiee(true);
            absence1.setMotifJustification("Certificat Médical");
            absence1.setNombreHeures(2);
            absence1.setEtudiant(etudiant2);
            absence1.setSeance(seance1);
            absence1.setSurveillant(surveillant);

            Absence absence2 = new Absence();
            absence2.setDateAbsence(LocalDate.of(2026, 2, 22));
            absence2.setEstJustifiee(false);
            absence2.setMotifJustification(null);
            absence2.setNombreHeures(2);
            absence2.setEtudiant(etudiant3);
            absence2.setSeance(seance2);
            absence2.setSurveillant(surveillant);

            absenceRepository.saveAll(List.of(absence1, absence2));

            Sanction sanction1 = new Sanction();
            sanction1.setDateEmission(LocalDate.now());
            sanction1.setType(TypeSanction.AVERTISSEMENT);
            sanction1.setMotif("Absences répétées non justifiées");
            sanction1.setTotalAbsencesAuMoment(6);
            sanction1.setEstTraitee(true);
            sanction1.setEtudiant(etudiant3);
            sanction1.setSurveillant(surveillant);
            sanctionRepository.save(sanction1);

            // ==========================================================
            // 6. FINANCE (Paiements, Recus)
            // ==========================================================
            Paiement paiement1 = new Paiement();
            paiement1.setReferencePaiement("PAY-2026-001");
            paiement1.setTypePaiement(TypePaiement.FRAIS_INSCRIPTION);
            paiement1.setMontant(2500.0);
            paiement1.setDatePaiement(LocalDate.of(2026, 1, 5));
            paiement1.setMode(ModePaiement.VIREMENT);
            paiement1.setStatut(StatutPaiement.PAYE);
            paiement1.setEtudiant(etudiant1);
            paiement1.setResponsableFinancier(respFin);

            Paiement paiement2 = new Paiement();
            paiement2.setReferencePaiement("PAY-2026-002");
            paiement2.setTypePaiement(TypePaiement.MENSUALITE);
            paiement2.setMontant(1500.0);
            paiement2.setDatePaiement(LocalDate.of(2026, 2, 1));
            paiement2.setMode(ModePaiement.ESPECES);
            paiement2.setStatut(StatutPaiement.PAYE);
            paiement2.setEtudiant(etudiant2);
            paiement2.setResponsableFinancier(respFin);

            paiementRepository.saveAll(List.of(paiement1, paiement2));

            Recu recu1 = new Recu();
            recu1.setNumeroRecu("REC-2026-0001");
            recu1.setDateEmission(LocalDate.of(2026, 1, 5));
            recu1.setMontantPaye(2500.0);
            recu1.setPaiement(paiement1);
            recuRepository.save(recu1);

            System.out.println("✅ Les données d'initialisation de l'ERP ont été insérées avec succès !");
        };
    }
}
