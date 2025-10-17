<%@ page import="java.util.List" %>
<%@ page import="com.example.medcore.dao.ConsultationDAO" %>
<%@ page import="com.example.medcore.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Module Medecin Generaliste</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style="background:#f8f9fa;">

<div class="container py-4">
    <h2 class="text-center mb-4">Bienvenue, Adilaitelhoucine (Generaliste)</h2>
    <h4 class="mb-4 text-primary">File d'attente des patients</h4>

    <table class="table table-bordered align-middle bg-white shadow-sm">
        <thead class="table-light">
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Consultation</th>
            <th>Actes</th>
<%--            <th>Prise directe</th>--%>
            <th>Demande-expertise</th>
            <th>Details De Consultation</th>
            <th>Dossier</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Patient> patients = (List<Patient>) request.getAttribute("patients");
            if (patients != null && !patients.isEmpty()) {
                for (Patient patient : patients) {
        %>
        <tr>
            <td><%= patient.getNom() %></td>
            <td><%= patient.getPrenom() %></td>
            <td><button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#consultationModal<%= patient.getId() %>">Consultation</button></td>
            <td><button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#actModal<%= patient.getId() %>">Acte</button></td>
<%--            <td><button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#directCareModal<%= patient.getId() %>">Directe</button></td>--%>
            <td><button class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#expertiseModal<%= patient.getId() %>">Demande</button></td>
            <td><button class="btn btn-sm btn-dark" data-bs-toggle="modal" data-bs-target="#consultDetailModal<%= patient.getId() %>">Details </button></td>
            <td><button class="btn btn-sm btn-secondary" data-bs-toggle="modal" data-bs-target="#medicalFileModal<%= patient.getId() %>">Dossier</button></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="8" class="text-center text-muted">Aucun patient trouvé.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<%-- Modals for each patient --%>
<%
    if (patients != null && !patients.isEmpty()) {
        for (Patient patient : patients) {
%>

<!-- Modal: Nouvelle Consultation -->
<div class="modal fade" id="consultationModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form action="<%= request.getContextPath() %>/addconsultation" method="post" class="modal-content bg-white shadow">
            <input type="hidden" name="patient_id" value="<%= patient.getId() %>">
            <div class="modal-header">
                <h5 class="modal-title">Nouvelle consultation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Motif</label>
                <input type="text" class="form-control mb-2" name="motif" required>
                <label class="form-label">Observations</label>
                <textarea class="form-control mb-2" name="observations" required></textarea>
                <label class="form-label">Diagnostic</label>
                <input type="text" class="form-control mb-2" name="diagnostic" required>
                <label class="form-label">Traitement</label>
                <textarea class="form-control mb-2" name="traitement" required></textarea>
                <label class="form-label">Coût</label>
                <input type="number" class="form-control mb-2" name="cout" required>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Enregistrer</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal: Détails des Consultations -->
<div class="modal fade" id="consultDetailModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-scrollable">
        <div class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">
                    Détails des consultations - <%= patient.getNom() %> <%= patient.getPrenom() %>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <!-- ✅ Scroll activé + limite de hauteur -->
            <div class="modal-body" style="max-height: 70vh; overflow-y: auto;">
                <%
                    List<Consultation> consultations = patient.getConsultations();
                    if (consultations != null && !consultations.isEmpty()) {
                %>
                <table class="table table-bordered align-middle">
                    <thead class="table-light">
                    <tr>
                        <th>Date</th>
                        <th>Motif</th>
                        <th>Observations</th>
                        <th>Diagnostic</th>
                        <th>Traitement</th>
                        <th>Coût</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Consultation c : consultations) { %>
                    <tr>
                        <td>
                            <%= c.getDateConsultation() != null
                                    ? c.getDateConsultation().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                                    : "-"
                            %>
                        </td>
                        <td><%= c.getMotif() %></td>
                        <td><%= c.getObservations() %></td>
                        <td><%= c.getDiagnostic() %></td>
                        <td><%= c.getTraitement() %></td>
                        <td><%= c.getCout() %> dh</td>
                        <td><%= c.getStatus() %></td>
                        <td>
                            <form action="<%=request.getContextPath()%>/changestatusconsultation" method="post" class="d-flex align-items-center">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="consultationId" value="<%= c.getId() %>">
                                <input type="hidden" name="patient_id" value="<%=c.getPatient().getId()%>">

                                <select name="status" class="form-select form-select-sm me-2">
                                    <option value="EN_ATTENTE"
                                            <%= (c.getStatus() != null && c.getStatus().name().equals("EN_ATTENTE")) || c.getStatus() == null ? "selected" : "" %>>
                                        En attente
                                    </option>

                                    <option value="EN_ATTENTE_AVIS_SPECIALISTE"
                                            <%= c.getStatus() != null && c.getStatus().name().equals("EN_ATTENTE_AVIS_SPECIALISTE") ? "selected" : "" %>>
                                        En attente avis spécialiste
                                    </option>

                                    <option value="TERMINEE"
                                            <%= c.getStatus() != null && c.getStatus().name().equals("TERMINEE") ? "selected" : "" %>>
                                        Terminée
                                    </option>
                                </select>


                                <button type="submit" class="btn btn-sm btn-primary">✔</button>
                            </form>

                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p class="text-center text-muted">Aucune consultation enregistrée pour ce patient.</p>
                <% } %>
            </div>
        </div>
    </div>
</div>


<!-- Modal: Actes -->
<div class="modal fade" id="actModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form class="modal-content bg-white shadow" action="<%= request.getContextPath() %>/addactetechnique" method="post">
            <div class="modal-header">
                <h5 class="modal-title">Ajouter acte technique</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">


                <input type="hidden" name="patient_id" value="<%= patient.getId() %>">


                <label class="form-label">Sélectionner une consultation</label>
                <%
                    com.example.medcore.dao.ConsultationDAO consultationDAO = new com.example.medcore.dao.ConsultationDAO();
                    java.util.List<com.example.medcore.model.Consultation> consultationslist = consultationDAO.getConsultationbyPatient(patient.getId());
                %>

                <% if (consultationslist != null && !consultationslist.isEmpty()) { %>
                <select class="form-select mb-3" name="consultation_id" required>
                    <% for (com.example.medcore.model.Consultation consultation : consultationslist) { %>
                    <option value="<%= consultation.getId() %>">
                        <%= consultation.getMotif() != null ? consultation.getMotif() : "Consultation #" + consultation.getId() %>
                    </option>
                    <% } %>
                </select>
                <% } else { %>
                <p class="text-muted small">⚠️ Aucune consultation trouvée pour ce patient.</p>
                <% } %>

                <!-- Acte type -->
                <label class="form-label">Type d'acte</label>
                <select class="form-select mb-2" name="actetyppe">
                    <option value="RADIOGRAPHIE">Radiographie</option>
                    <option value="ECHOGRAPHIE">Echographie</option>
                    <option value="IRM">IRM</option>
                    <option value="ELECTROCARDIOGRAMME">ECG</option>
                    <option value="ANALYSE_SANG">Analyse sang</option>
                    <option value="ANALYSE_URINE">Analyse urine</option>
                </select>

                <!-- Résultat -->
                <label class="form-label">Résultat</label>
                <input type="text" class="form-control mb-2" name="result">
            </div>

            <div class="modal-footer">
                <button type="submit" class="btn btn-info">Ajouter</button>
            </div>
        </form>
    </div>
</div>




<!-- Modal: Tele-expertise -->
<div class="modal fade" id="expertiseModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form class="modal-content bg-white shadow" method="get" action="<%=request.getContextPath()%>/creneau">

            <div class="modal-header">
                <h5 class="modal-title">Demande expertise</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <div class="modal-body">
                <!-- Consultations disponibles -->
                <label class="form-label" for="consultation<%= patient.getId() %>">Consultations disponibles</label>
                <select class="form-select mb-3" id="consultation<%= patient.getId() %>" name="consultationId" required>
                    <option value="">-- Sélectionnez une consultation --</option>
                    <%
                        List<Consultation> AvConsultations = consultationDAO.getAvailableConsultations(patient.getId());
                        if (AvConsultations != null && !AvConsultations.isEmpty()) {
                            for (Consultation consultation : AvConsultations) {
                    %>
                    <option value="<%= consultation.getId() %>">
                        <%= consultation.getMotif() %> - <%= consultation.getDateConsultation() %>
                    </option>
                    <%
                        }
                    } else {
                    %>
                    <option disabled>Aucune consultation disponible</option>
                    <%
                        }
                    %>
                </select>


                <label class="form-label" for="medecin<%= patient.getId() %>">Médecin disponible</label>
                <select class="form-select mb-3" id="medecin<%= patient.getId() %>" name="medecinId" required>
                    <option value="">-- Sélectionnez un médecin --</option>
                    <%
                        List<MedecinSpecialiste> specialisteList = (List<MedecinSpecialiste>) request.getAttribute("specialisteList");
                        if (specialisteList != null && !specialisteList.isEmpty()) {
                            for (MedecinSpecialiste medecinSpecialiste : specialisteList) {
                    %>
                    <option value="<%= medecinSpecialiste.getId() %>">
                        <%= medecinSpecialiste.getNom() %> -- <%= medecinSpecialiste.getSpecialite() %>
                    </option>
                    <%
                        }
                    } else {
                    %>
                    <option disabled>Aucun Specialiste disponible</option>
                    <%
                        }
                    %>
                </select>

                <!-- Button to go to next page to select créneau -->
                <button type="submit" class="btn btn-primary w-100">Choisir créneau</button>

                <!-- Question (optional on next page if needed) -->
<%--                <label class="form-label mt-3" for="question<%= patient.getId() %>">Question</label>--%>
<%--                <textarea class="form-control mb-3" id="question<%= patient.getId() %>" name="question" rows="3"></textarea>--%>

                <!-- Priorité (optional on next page if needed) -->
<%--                <label class="form-label" for="priorite<%= patient.getId() %>">Priorité</label>--%>
<%--                <select class="form-select" id="priorite<%= patient.getId() %>" name="priorite">--%>
<%--                    <option value="">-- Sélectionnez --</option>--%>
<%--                    <option value="URGENTE">Urgente</option>--%>
<%--                    <option value="NORMALE">Normale</option>--%>
<%--                    <option value="NON_URGENTE">Non urgente</option>--%>
<%--                </select>--%>
            </div>
        </form>
    </div>
</div>





<!-- Modal: Dossier Medical -->
<div class="modal fade" id="medicalFileModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Dossier médical</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <%
                    DossierMedical dossierMedical = patient.getDossierMedical();
                    SignesVitaux signesVitaux=patient.getSignesVitaux();
                    if (dossierMedical != null ) {
                %>
                <p><strong>Antécédents:</strong> <%= dossierMedical.getAntecedents() %></p>
                <p><strong>Allergies:</strong> <%= dossierMedical.getAllergies() %></p>
                <p><strong>Traitements en cours:</strong> <%= dossierMedical.getTraitementEnCours() %></p>
                <p><strong>Actes techniques:</strong> Radiographie</p>
                <hr>
                <h5 class="modal-title">Signes Vitaux </h5>
                <hr>

                <p><strong>tension:</strong> <%= signesVitaux.getTension()%></p>
                <p><strong>frequenceCardiaque:</strong> <%= signesVitaux.getFrequenceCardiaque()%></p>
                <p><strong>temperature:</strong> <%=  signesVitaux.getTemperature()%></p>
<%--               <p><strong>frequenceRespiratoire techniques:</strong> <%=signesVitaux.getFrequenceRespiratoire()%> </p>--%>
                <p><strong>poids :</strong> <%= signesVitaux.getPoids()%> </p>
                <p><strong>taille :</strong><%= signesVitaux.getTaille()%> </p>

                <%
                } else {
                %>
                <p class="text-muted text-center">Aucun dossier médical disponible pour ce patient.</p>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>


<% } } %>

<%--<script>--%>
<%--    document.getElementById('specialite').addEventListener('change', function() {--%>
<%--        const specialite = this.value;--%>
<%--        const medecinSelect = document.getElementById('medecin');--%>
<%--        const creneauSelect = document.getElementById('creneau');--%>

<%--        // Clear previous options--%>
<%--        medecinSelect.innerHTML = '<option value="">-- Sélectionnez --</option>';--%>
<%--        creneauSelect.innerHTML = '<option value="">-- Sélectionnez --</option>';--%>

<%--        if (!specialite) return;--%>

<%--        // Fetch available doctors for selected specialty--%>
<%--        fetch(`/api/medecinsDisponibles?specialite=${specialite}`)--%>
<%--            .then(response => response.json())--%>
<%--            .then(data => {--%>
<%--                data.forEach(doc => {--%>
<%--                    const option = document.createElement('option');--%>
<%--                    option.value = doc.id;--%>
<%--                    option.textContent = doc.nom + ' ' + doc.prenom;--%>
<%--                    medecinSelect.appendChild(option);--%>
<%--                });--%>
<%--            });--%>
<%--    });--%>

<%--    document.getElementById('medecin').addEventListener('change', function() {--%>
<%--        const medecinId = this.value;--%>
<%--        const creneauSelect = document.getElementById('creneau');--%>

<%--        creneauSelect.innerHTML = '<option value="">-- Sélectionnez --</option>';--%>

<%--        if (!medecinId) return;--%>

<%--        // Fetch available creneaux for selected doctor--%>
<%--        fetch(`/api/creneauxDisponibles?medecinId=${medecinId}`)--%>
<%--            .then(response => response.json())--%>
<%--            .then(data => {--%>
<%--                data.forEach(slot => {--%>
<%--                    const option = document.createElement('option');--%>
<%--                    option.value = slot.id;--%>
<%--                    option.textContent = slot.dateHeureDebut + ' - ' + slot.dateHeureFin;--%>
<%--                    creneauSelect.appendChild(option);--%>
<%--                });--%>
<%--            });--%>
<%--    });--%>
<%--</script>--%>


</body>
</html>
