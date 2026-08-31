package com.gestion.etablissement.scolaire.developpementerp.services.pdf;

import com.gestion.etablissement.scolaire.developpementerp.model.entities.Etudiant;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Note;
import com.gestion.etablissement.scolaire.developpementerp.model.entities.Paiement;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class PdfGenerationService {

    public byte[] generateBulletinPdf(Etudiant etudiant, List<Note> notes, Double moyenneGenerale) {
        log.debug("Generating PDF Bulletin for etudiant ID: {}", etudiant.getId());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            // Header Font
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

            // Header Title
            Paragraph title = new Paragraph("ÉTABLISSEMENT SCOLAIRE - ERP", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("BULLETIN DE NOTES OFFICIEL", subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            subTitle.setSpacingAfter(20);
            document.add(subTitle);

            // Student Information Block
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingAfter(15);

            String etudiantNom = (etudiant.getNom() != null ? etudiant.getNom() : "") + " " + (etudiant.getPrenom() != null ? etudiant.getPrenom() : "");
            String classeNom = (etudiant.getClasse() != null && etudiant.getClasse().getNom() != null) ? etudiant.getClasse().getNom() : "N/A";

            infoTable.addCell(createCell("Nom & Prénom: " + etudiantNom, boldFont, Element.ALIGN_LEFT, false));
            infoTable.addCell(createCell("Date d'édition: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont, Element.ALIGN_RIGHT, false));
            infoTable.addCell(createCell("Classe: " + classeNom, normalFont, Element.ALIGN_LEFT, false));
            infoTable.addCell(createCell("ID Étudiant: " + etudiant.getId(), normalFont, Element.ALIGN_RIGHT, false));

            document.add(infoTable);

            // Notes Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 1, 1, 3});
            table.setSpacingAfter(20);

            // Header Cells
            addHeaderCell(table, "Matière", boldFont);
            addHeaderCell(table, "Note / 20", boldFont);
            addHeaderCell(table, "Coeff.", boldFont);
            addHeaderCell(table, "Appréciation", boldFont);

            if (notes != null && !notes.isEmpty()) {
                for (Note note : notes) {
                    String matiereStr = (note.getEvaluation() != null && note.getEvaluation().getMatiere() != null)
                            ? note.getEvaluation().getMatiere().getIntitule()
                            : "Matière";
                    String noteValStr = note.getValeur() != null ? String.format("%.2f", note.getValeur()) : "-";
                    String coeffStr = (note.getEvaluation() != null && note.getEvaluation().getCoefficient() != null)
                            ? String.valueOf(note.getEvaluation().getCoefficient())
                            : "1";
                    String appStr = note.getAppreciation() != null ? note.getAppreciation() : "";

                    table.addCell(createCell(matiereStr, normalFont, Element.ALIGN_LEFT, true));
                    table.addCell(createCell(noteValStr, normalFont, Element.ALIGN_CENTER, true));
                    table.addCell(createCell(coeffStr, normalFont, Element.ALIGN_CENTER, true));
                    table.addCell(createCell(appStr, normalFont, Element.ALIGN_LEFT, true));
                }
            } else {
                PdfPCell emptyCell = new PdfPCell(new Phrase("Aucune note disponible", normalFont));
                emptyCell.setColspan(4);
                emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(emptyCell);
            }

            document.add(table);

            // Overall Average Footer
            Paragraph gpaParagraph = new Paragraph("MOYENNE GÉNÉRALE : " + String.format("%.2f / 20", moyenneGenerale != null ? moyenneGenerale : 0.0), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.RED));
            gpaParagraph.setAlignment(Element.ALIGN_RIGHT);
            gpaParagraph.setSpacingAfter(30);
            document.add(gpaParagraph);

            // Signature Block
            Paragraph signature = new Paragraph("Cachet et Signature du Directeur :", boldFont);
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            document.close();
        } catch (Exception e) {
            log.error("Error generating PDF Bulletin: {}", e.getMessage(), e);
        }

        return out.toByteArray();
    }

    public byte[] generateRecuPaiementPdf(Paiement paiement) {
        log.debug("Generating PDF Recu for paiement ID: {}", paiement.getId());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A5, 20, 20, 20, 20);
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLUE);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

            Paragraph title = new Paragraph("REÇU DE PAIEMENT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15);
            document.add(title);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingAfter(15);

            String ref = paiement.getReferencePaiement() != null ? paiement.getReferencePaiement() : "REC-" + paiement.getId();
            String datePaiement = paiement.getDatePaiement() != null ? paiement.getDatePaiement().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String etudiantNom = (paiement.getEtudiant() != null)
                    ? (paiement.getEtudiant().getNom() + " " + paiement.getEtudiant().getPrenom())
                    : "N/A";
            String montantStr = paiement.getMontant() != null ? String.format("%.2f DH", paiement.getMontant()) : "0.00 DH";
            String modeStr = paiement.getMode() != null ? paiement.getMode().name() : "N/A";
            String statutStr = paiement.getStatut() != null ? paiement.getStatut().name() : "N/A";

            table.addCell(createCell("Référence :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(ref, normalFont, Element.ALIGN_LEFT, true));

            table.addCell(createCell("Date du paiement :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(datePaiement, normalFont, Element.ALIGN_LEFT, true));

            table.addCell(createCell("Étudiant :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(etudiantNom, normalFont, Element.ALIGN_LEFT, true));

            table.addCell(createCell("Montant réglé :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(montantStr, boldFont, Element.ALIGN_LEFT, true));

            table.addCell(createCell("Mode de paiement :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(modeStr, normalFont, Element.ALIGN_LEFT, true));

            table.addCell(createCell("Statut :", boldFont, Element.ALIGN_LEFT, true));
            table.addCell(createCell(statutStr, boldFont, Element.ALIGN_LEFT, true));

            document.add(table);

            Paragraph footer = new Paragraph("Document généré automatiquement par l'ERP Scolaire. Merci pour votre règlement.", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            log.error("Error generating PDF Recu: {}", e.getMessage(), e);
        }

        return out.toByteArray();
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private PdfPCell createCell(String text, Font font, int alignment, boolean border) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        if (!border) {
            cell.setBorder(Rectangle.NO_BORDER);
        }
        return cell;
    }
}
