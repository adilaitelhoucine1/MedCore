<%@ page import="com.example.medcore.model.Patient" %>
<%@ page import="java.util.List" %>
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
            <th>Details</th>
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
            <td><button class="btn btn-sm btn-dark" data-bs-toggle="modal" data-bs-target="#consultDetailModal<%= patient.getId() %>">Details</button></td>
            <td><button class="btn btn-sm btn-secondary" data-bs-toggle="modal" data-bs-target="#medicalFileModal<%= patient.getId() %>">Dossier</button></td>
        </tr>
        <%
            } // end for
        } else {
        %>
        <tr>
            <td colspan="8" class="text-center text-muted">Aucun patient trouve.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<!-- ✅ All modals go outside the table -->
<%
    if (patients != null && !patients.isEmpty()) {
        for (Patient patient : patients) {
%>

<!-- Modal Consultation -->
<div class="modal fade" id="consultationModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form action="<%= request.getContextPath() %>/addconsultation" class="modal-content bg-white shadow" method="post">
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

                <label class="form-label">cout</label>
                <input type="number" class="form-control mb-2" name="cout" required>

            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Enregistrer</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Acte Technique -->
<div class="modal fade" id="actModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <form class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Ajouter acte technique</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Type d'acte</label>
                <select class="form-select mb-2">
                    <option>Radiographie</option>
                    <option>Echographie</option>
                    <option>IRM</option>
                    <option>ECG</option>
                    <option>Analyse sang</option>
                    <option>Analyse urine</option>
                </select>
                <label class="form-label">Resultat</label>
                <input type="text" class="form-control mb-2">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-info">Ajouter</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Prise en charge directe -->
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

<!-- Modal Tele-expertise -->
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

<!-- Modal Details Consultation -->
<div class="modal fade" id="consultDetailModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Details consultation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>Motif:</strong> Toux</p>
                <p><strong>Diagnostic:</strong> Grippe</p>
                <p><strong>Actes:</strong> Radiographie, Analyse sang</p>
                <p><strong>Cout total:</strong> 350 dh</p>
            </div>
        </div>
    </div>
</div>

<!-- Modal Dossier Medical -->
<div class="modal fade" id="medicalFileModal<%= patient.getId() %>" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content bg-white shadow">
            <div class="modal-header">
                <h5 class="modal-title">Dossier medical</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>Antecedents:</strong> Asthme</p>
                <p><strong>Allergies:</strong> Penicilline</p>
                <p><strong>Traitements en cours:</strong> Inhalateur</p>
                <ul>
                    <li>2025-10-01: Grippe</li>
                    <li>2025-09-15: Controle</li>
                </ul>
                <p><strong>Actes techniques:</strong> Radiographie</p>
            </div>
        </div>
    </div>
</div>

<%
        } // end for modals
    }
%>

</body>
</html>
