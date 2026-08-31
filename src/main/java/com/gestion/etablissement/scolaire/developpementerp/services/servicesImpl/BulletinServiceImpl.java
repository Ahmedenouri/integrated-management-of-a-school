package com.gestion.etablissement.scolaire.developpementerp.services.servicesImpl;

import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoRequests.BulletinRequest;
import com.gestion.etablissement.scolaire.developpementerp.model.dtos.dtoResponce.BulletinResponce;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Bulletin;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import com.gestion.etablissement.scolaire.developpementerp.model.exceptions.ResourceNotFoundException;
import com.gestion.etablissement.scolaire.developpementerp.model.mappers.IBulletinMapper;
import com.gestion.etablissement.scolaire.developpementerp.repositories.BulletinRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.DirecteurRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.EtudiantRepository;
import com.gestion.etablissement.scolaire.developpementerp.repositories.NoteRepository;
import com.gestion.etablissement.scolaire.developpementerp.services.IBulletinService;
import com.gestion.etablissement.scolaire.developpementerp.services.INoteService;
import com.gestion.etablissement.scolaire.developpementerp.services.pdf.PdfGenerationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BulletinServiceImpl implements IBulletinService {

    private final IBulletinMapper bulletinMapper;
    private final BulletinRepository bulletinRepository;
    private final EtudiantRepository etudiantRepository;
    private final DirecteurRepository directeurRepository;
    private final NoteRepository noteRepository;
    private final INoteService noteService;
    private final PdfGenerationService pdfGenerationService;

    @Override
    public BulletinResponce addBulletin(BulletinRequest bulletinRequest) {
        log.debug("Adding bulletin for etudiant ID: {}", bulletinRequest.getEtudiantId());

        Bulletin bulletin = bulletinMapper.map(bulletinRequest);
        if (bulletin.getDateGeneration() == null) {
            bulletin.setDateGeneration(LocalDate.now());
        }
        assignRelations(bulletin, bulletinRequest.getEtudiantId(), bulletinRequest.getDirecteurId());

        Bulletin savedBulletin = bulletinRepository.save(bulletin);
        return bulletinMapper.map(savedBulletin);
    }

    @Override
    public BulletinResponce updateBulletin(Long idBulletin, BulletinRequest bulletinRequest) {
        log.debug("Updating bulletin ID: {}", idBulletin);

        Bulletin existingBulletin = findBulletinOrThrow(idBulletin);
        bulletinMapper.updateFromRequest(bulletinRequest, existingBulletin);
        assignRelations(existingBulletin, bulletinRequest.getEtudiantId(), bulletinRequest.getDirecteurId());

        Bulletin savedBulletin = bulletinRepository.save(existingBulletin);
        return bulletinMapper.map(savedBulletin);
    }

    @Override
    public void deleteBulletin(Long idBulletin) {
        log.debug("Deleting bulletin ID: {}", idBulletin);
        if (!bulletinRepository.existsById(idBulletin)) {
            throw new ResourceNotFoundException("Bulletin not found with ID: " + idBulletin);
        }
        bulletinRepository.deleteById(idBulletin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BulletinResponce> getAllBulletins() {
        log.debug("Fetching all bulletins");
        return bulletinMapper.mapList(bulletinRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public BulletinResponce getBulletinById(Long idBulletin) {
        log.debug("Fetching bulletin ID: {}", idBulletin);
        return bulletinMapper.map(findBulletinOrThrow(idBulletin));
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateBulletinPdf(Long etudiantId) {
        log.debug("Generating Bulletin PDF for etudiant ID: {}", etudiantId);
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + etudiantId));

        List<Note> notes = noteRepository.findByEtudiantId(etudiantId);
        Double gpa = noteService.calculateMoyenneGenerale(etudiantId);

        return pdfGenerationService.generateBulletinPdf(etudiant, notes, gpa);
    }

    private Bulletin findBulletinOrThrow(Long idBulletin) {
        return bulletinRepository.findById(idBulletin)
                .orElseThrow(() -> new ResourceNotFoundException("Bulletin not found with ID: " + idBulletin));
    }

    private void assignRelations(Bulletin bulletin, Long etudiantId, Long directeurId) {
        if (etudiantId != null) {
            bulletin.setEtudiant(etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with ID: " + etudiantId)));
        }
        if (directeurId != null) {
            bulletin.setDirecteur(directeurRepository.findById(directeurId)
                    .orElseThrow(() -> new ResourceNotFoundException("Directeur not found with ID: " + directeurId)));
        }
    }
}
