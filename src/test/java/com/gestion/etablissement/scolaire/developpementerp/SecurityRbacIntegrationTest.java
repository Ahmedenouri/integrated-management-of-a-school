package com.gestion.etablissement.scolaire.developpementerp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityRbacIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("0. Unauthenticated Access Tests")
    class UnauthenticatedTests {

        @Test
        @DisplayName("Unauthenticated request to dashboard returns 401 Unauthorized")
        void testUnauthenticatedDashboardReturns401() throws Exception {
            mockMvc.perform(get("/api-dashboard/stats"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.error").value("Unauthorized"));
        }

        @Test
        @DisplayName("Unauthenticated request to profile returns 401 Unauthorized")
        void testUnauthenticatedProfileReturns401() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.error").value("Unauthorized"));
        }
    }

    @Nested
    @DisplayName("1. ROLE_DIRECTEUR (Super-Admin) Tests")
    class DirecteurTests {

        @Test
        @WithMockUser(username = "ahmed.ennouri@school.ma", roles = {"DIRECTEUR"})
        @DisplayName("Directeur can access global dashboard stats")
        void testDirecteurCanAccessDashboardStats() throws Exception {
            mockMvc.perform(get("/api-dashboard/stats"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "ahmed.ennouri@school.ma", roles = {"DIRECTEUR"})
        @DisplayName("Directeur can access user administration")
        void testDirecteurCanAccessAllUsers() throws Exception {
            mockMvc.perform(get("/api-user/getAllUsers"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "ahmed.ennouri@school.ma", roles = {"DIRECTEUR"})
        @DisplayName("Directeur can view own profile")
        void testDirecteurCanAccessOwnProfile() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("ahmed.ennouri@school.ma"));
        }
    }

    @Nested
    @DisplayName("2. ROLE_RESPONSABLE_FINANCIER Tests")
    class ResponsableFinancierTests {

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier can access dedicated financial dashboard")
        void testFinancierCanAccessDashboardFinancier() throws Exception {
            mockMvc.perform(get("/api-dashboard/financier"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier can access all payments")
        void testFinancierCanAccessAllPayments() throws Exception {
            mockMvc.perform(get("/api-paiement/getAllPaiements"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier FORBIDDEN from global dashboard stats")
        void testFinancierForbiddenFromDashboardStats() throws Exception {
            mockMvc.perform(get("/api-dashboard/stats"))
                    .andExpect(status().isForbidden())
                    .andExpect(jsonPath("$.error").value("Forbidden"));
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier FORBIDDEN from discipline dashboard")
        void testFinancierForbiddenFromDashboardDiscipline() throws Exception {
            mockMvc.perform(get("/api-dashboard/discipline"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier FORBIDDEN from grades/notes")
        void testFinancierForbiddenFromNotes() throws Exception {
            mockMvc.perform(get("/api-note/getAllNotes"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier FORBIDDEN from absences")
        void testFinancierForbiddenFromAbsences() throws Exception {
            mockMvc.perform(get("/api-absence/getAllAbsences"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier FORBIDDEN from sanctions")
        void testFinancierForbiddenFromSanctions() throws Exception {
            mockMvc.perform(get("/api-sanction/getAllSanctions"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "karim.bennani@school.ma", roles = {"RESPONSABLE_FINANCIER"})
        @DisplayName("Financier can access own profile")
        void testFinancierCanAccessOwnProfile() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("karim.bennani@school.ma"));
        }
    }

    @Nested
    @DisplayName("3. ROLE_SURVEILLANT Tests")
    class SurveillantTests {

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant can access discipline dashboard")
        void testSurveillantCanAccessDisciplineDashboard() throws Exception {
            mockMvc.perform(get("/api-dashboard/discipline"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant can access all absences")
        void testSurveillantCanAccessAbsences() throws Exception {
            mockMvc.perform(get("/api-absence/getAllAbsences"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant can access all sanctions")
        void testSurveillantCanAccessSanctions() throws Exception {
            mockMvc.perform(get("/api-sanction/getAllSanctions"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant FORBIDDEN from financial dashboard")
        void testSurveillantForbiddenFromFinancialDashboard() throws Exception {
            mockMvc.perform(get("/api-dashboard/financier"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant FORBIDDEN from payments")
        void testSurveillantForbiddenFromPayments() throws Exception {
            mockMvc.perform(get("/api-paiement/getAllPaiements"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant FORBIDDEN from deleting students")
        void testSurveillantForbiddenFromDeletingStudents() throws Exception {
            mockMvc.perform(delete("/api-etudiant/delete-Etudiant/1"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "rachid.tazi@school.ma", roles = {"SURVEILLANT"})
        @DisplayName("Surveillant can access own profile")
        void testSurveillantCanAccessOwnProfile() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("rachid.tazi@school.ma"));
        }
    }

    @Nested
    @DisplayName("4. ROLE_PROFESSEUR Tests")
    class ProfesseurTests {

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur can access own assigned sessions")
        void testProfesseurCanAccessOwnSessions() throws Exception {
            mockMvc.perform(get("/api-seance/mes-seances"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur can access own assigned classes")
        void testProfesseurCanAccessOwnClasses() throws Exception {
            mockMvc.perform(get("/api-professeur/mes-classes"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur FORBIDDEN from global dashboard")
        void testProfesseurForbiddenFromDashboardStats() throws Exception {
            mockMvc.perform(get("/api-dashboard/stats"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur FORBIDDEN from all notes endpoint")
        void testProfesseurForbiddenFromAllNotes() throws Exception {
            mockMvc.perform(get("/api-note/getAllNotes"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur FORBIDDEN from financial operations")
        void testProfesseurForbiddenFromFinances() throws Exception {
            mockMvc.perform(get("/api-paiement/getAllPaiements"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur FORBIDDEN from creating seances (logistics)")
        void testProfesseurForbiddenFromAddingSeances() throws Exception {
            mockMvc.perform(post("/api-seance/add-Seance")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"jour\":\"LUNDI\"}"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "hassan.chraibi@school.ma", roles = {"PROFESSEUR"})
        @DisplayName("Professeur can view own profile")
        void testProfesseurCanAccessOwnProfile() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("hassan.chraibi@school.ma"));
        }
    }

    @Nested
    @DisplayName("5. ROLE_ETUDIANT Tests")
    class EtudiantTests {

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access personal profile")
        void testEtudiantCanAccessOwnProfile() throws Exception {
            mockMvc.perform(get("/api-profile/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value("youssef.alami@student.ma"));
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own notes")
        void testEtudiantCanAccessOwnNotes() throws Exception {
            mockMvc.perform(get("/api-note/mes-notes"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own absences")
        void testEtudiantCanAccessOwnAbsences() throws Exception {
            mockMvc.perform(get("/api-absence/mes-absences"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own sanctions")
        void testEtudiantCanAccessOwnSanctions() throws Exception {
            mockMvc.perform(get("/api-sanction/mes-sanctions"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own payments")
        void testEtudiantCanAccessOwnPayments() throws Exception {
            mockMvc.perform(get("/api-paiement/mes-paiements"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own receipts")
        void testEtudiantCanAccessOwnReceipts() throws Exception {
            mockMvc.perform(get("/api-recu/mes-recus"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant can access own schedule")
        void testEtudiantCanAccessOwnSchedule() throws Exception {
            mockMvc.perform(get("/api-emploi-du-temps/mon-emploi"))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant FORBIDDEN from getAllNotes")
        void testEtudiantForbiddenFromAllNotes() throws Exception {
            mockMvc.perform(get("/api-note/getAllNotes"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant FORBIDDEN from getAllAbsences")
        void testEtudiantForbiddenFromAllAbsences() throws Exception {
            mockMvc.perform(get("/api-absence/getAllAbsences"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant FORBIDDEN from dashboards")
        void testEtudiantForbiddenFromDashboard() throws Exception {
            mockMvc.perform(get("/api-dashboard/stats"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(username = "youssef.alami@student.ma", roles = {"ETUDIANT"})
        @DisplayName("Etudiant FORBIDDEN from user administration")
        void testEtudiantForbiddenFromUserAdmin() throws Exception {
            mockMvc.perform(get("/api-user/getAllUsers"))
                    .andExpect(status().isForbidden());
        }
    }
}
