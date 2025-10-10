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
    <h2 class="dashboard-title text-center">Bienvenue, Adilaitelhoucine (Generaliste)</h2>
    <h2 class="mb-4 text-primary">File d'attente des patients</h2>
    <table class="table table-bordered align-middle">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Heure d'arrivee</th>
            <th>Consultation</th>
            <th>Actes</th>
            <th>Prise en charge directe</th>
            <th>Tele-expertise</th>
            <th>Details consultation</th>
            <th>Dossier medical</th>
        </tr>
        </thead>
        <tbody>

        <%
            List<Patient> patients = (List<Patient>) request.getAttribute("patients");

            if (patients != null) {
                for (Patient patient : patients) {
        %>
        <tr>
            <td><%= patient.getNom() %></td>
            <td><%= patient.getPrenom() %></td>
            <td>
                <button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#consultationModal">Consultation</button>
            </td>
            <td>
                <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#actModal">Acte</button>
            </td>
            <td>
                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#directCareModal">Directe</button>
            </td>
            <td>
                <button class="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#expertiseModal">Tele</button>
            </td>
            <td>
                <button class="btn btn-sm btn-dark" data-bs-toggle="modal" data-bs-target="#consultDetailModal">Details</button>
            </td>
            <td>
                <button class="btn btn-sm btn-secondary" data-bs-toggle="modal" data-bs-target="#medicalFileModal">Dossier</button>
            </td>
        </tr>
        <%
            } // end for
        } else {
        %>
        <tr>
            <td colspan="7" class="text-center text-muted">No patients found.</td>
        </tr>
        <%
            }
        %>


        </tbody>
    </table>
</div>

<!-- Modal Consultation -->
<div class="modal fade" id="consultationModal" tabindex="-1" aria-labelledby="consultationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="consultationModalLabel">Nouvelle consultation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Motif</label>
                <input type="text" class="form-control mb-2">
                <label class="form-label">Observations</label>
                <textarea class="form-control mb-2"></textarea>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Enregistrer consultation</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Acte Technique -->
<div class="modal fade" id="actModal" tabindex="-1" aria-labelledby="actModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="actModalLabel">Ajouter acte technique</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Type d'acte</label>
                <select class="form-select mb-2">
                    <option>Radiographie</option>
                    <option>Echographie</option>
                    <option>IRM</option>
                    <option>ECG</option>
                    <option>Laser dermatologique</option>
                    <option>Fond d'oeil</option>
                    <option>Analyse sang</option>
                    <option>Analyse urine</option>
                </select>
                <label class="form-label">Resultat</label>
                <input type="text" class="form-control mb-2">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-info">Ajouter acte</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Prise en charge directe -->
<div class="modal fade" id="directCareModal" tabindex="-1" aria-labelledby="directCareModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="directCareModalLabel">Prise en charge directe</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <label class="form-label">Diagnostic</label>
                <input type="text" class="form-control mb-2">
                <label class="form-label">Traitement</label>
                <input type="text" class="form-control mb-2">
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-warning">Cloturer consultation</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Tele-expertise -->
<div class="modal fade" id="expertiseModal" tabindex="-1" aria-labelledby="expertiseModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="expertiseModalLabel">Demande tele-expertise</h5>
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
                <button type="submit" class="btn btn-danger">Envoyer demande</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal Details Consultation -->
<div class="modal fade" id="consultDetailModal" tabindex="-1" aria-labelledby="consultDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="consultDetailModalLabel">Details consultation</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>Motif:</strong> Toux</p>
                <p><strong>Diagnostic:</strong> Grippe</p>
                <p><strong>Actes:</strong> Radiographie, Analyse sang</p>
                <p><strong>Cout total:</strong> 350dh</p>
            </div>
        </div>
    </div>
</div>

<!-- Modal Dossier Medical -->
<div class="modal fade" id="medicalFileModal" tabindex="-1" aria-labelledby="medicalFileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="medicalFileModalLabel">Dossier medical</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>Antecedents:</strong> Asthme</p>
                <p><strong>Allergies:</strong> Penicilline</p>
                <p><strong>Traitements en cours:</strong> Inhalateur</p>
                <p><strong>Historique consultations:</strong></p>
                <ul>
                    <li>2025-10-01: Grippe</li>
                    <li>2025-09-15: Controle</li>
                </ul>
                <p><strong>Actes techniques:</strong> Radiographie</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>