<%@ page import="com.example.medcore.model.Patient" %>
<%@ page import="com.example.medcore.model.Consultation" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.medcore.model.DossierMedical" %>
<%@ page import="com.example.medcore.model.SignesVitaux" %>
<%@ page import="com.example.medcore.dao.ConsultationDAO" %>
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
            <th>Prise directe</th>
            <th>Tele-expertise</th>
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
            <td><button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#directCareModal<%= patient.getId() %>">Directe</button></td>
            <td><button class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#expertiseModal<%= patient.getId() %>">Tele</button></td>
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
                        <td><%= c.getDateConsultation() != null ? c.getDateConsultation() : "-" %></td>
                        <td><%= c.getMotif() %></td>
                        <td><%= c.getObservations() %></td>
                        <td><%= c.getDiagnostic() %></td>
                        <td><%= c.getTraitement() %></td>
                        <td><%= c.getCout() %> dh</td>
                        <td><%= c.getStatus() %></td>
                        <td>
                            <form action="consultation" method="post" class="d-flex align-items-center">
                                <input type="hidden" name="action" value="updateStatus">
                                <input type="hidden" name="consultationId" value="<%= c.getId() %>">

                                <select name="status" class="form-select form-select-sm me-2">
                                    <option value="EN_COURS" <%= "EN_COURS".equals(c.getStatus()) ? "selected" : "" %>>En cours</option>
                                    <option value="EN_ATTENTE_AVIS_SPECIALISTE" <%= "EN_ATTENTE_AVIS_SPECIALISTE".equals(c.getStatus()) ? "selected" : "" %>>En attente avis spécialiste</option>
                                    <option value="TERMINEE" <%= "TERMINEE".equals(c.getStatus()) ? "selected" : "" %>>Terminée</option>
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


<!-- Modal: Prise en charge directe -->
<div class="modal fade" id="directCareModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Prise en charge directe</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Diagnostic</label>
                <input type="text" class="form-control mb-2">
                <label class="form-label">Traitement</label>
                <input type="text" class="form-control mb-2">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-warning">Cloturer</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal: Tele-expertise -->
<div class="modal fade" id="expertiseModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Demande tele-expertise</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Specialite</label>
                <select class="form-select mb-2">
                    <option>Cardiologie</option>
                    <option>Pneumologie</option>
                    <option>Dermatologie</option>
                </select>
                <label class="form-label">Question</label>
                <textarea class="form-control mb-2"></textarea>
                <label class="form-label">Priorite</label>
                <select class="form-select">
                    <option>Urgente</option>
                    <option>Normale</option>
                    <option>Non urgente</option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-danger">Envoyer</button>
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

</body>
</html>
