<%@ page import="com.example.medcore.model.Patient" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil Infirmier </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #e9f5ff 0%, #f6f7fb 100%);
            min-height: 100vh;
        }
        .dashboard-title {
            margin: 30px 0 10px 0;
            color: #1677ff;
            font-weight: 700;
        }
        .logout-btn {
            position: absolute;
            top: 18px;
            right: 28px;
        }
        .table-actions .btn {
            margin-right: 6px;
            margin-bottom: 4px;
        }
    </style>
</head>
<body>
<a href="#" class="btn btn-outline-danger logout-btn">Deconnexion</a>


<%
    String error = request.getParameter("error");
    if (error != null) {
%>
<div class="alert alert-danger"><%= error %></div>
<% } %>


<div class="container">
    <h2 class="dashboard-title text-center">Bienvenue, Adilaitelhoucine (Infirmier)</h2>


    <div class="row mb-4">
        <div class="col-12">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#patientModal">
                + Create new patient
            </button>
        </div>
    </div>

    <!-- Modal: Create new patient -->
    <div class="modal fade" id="patientModal" tabindex="-1" aria-labelledby="patientModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="registerPatient" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="patientModalLabel">Administrative information</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control mb-2" name="nom" placeholder="Name" required>
                        <input type="text" class="form-control mb-2" name="prenom" placeholder="First name" required>
                        <input type="date" class="form-control mb-2" name="dateNaissance" placeholder="Birth date" required>
                        <input type="text" class="form-control mb-2" name="numSecu" placeholder="Social Security Number" required>
                        <input type="text" class="form-control mb-2" name="adresse" placeholder="Address">
                        <input type="tel" class="form-control mb-2" name="telephone" placeholder="Phone">
                        <input type="text" class="form-control mb-2" name="mutuelle" placeholder="Insurance">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <h3 class="mt-5 mb-3 text-primary">List of patients</h3>
    <table class="table table-striped table-bordered align-middle">
        <thead>
        <tr>
            <th>Name</th>
            <th>First name</th>
            <th>Birth date</th>
            <th>Social Security Number</th>
            <th>Phone</th>
            <th>Insurance</th>
            <th>Actions</th>
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
            <td><%= patient.getDateNaissance() %></td>
            <td><%= patient.getNumSecu() %></td>
            <td><%= patient.getTelephone() %></td>
            <td><%= patient.getMutuelle() %></td>
            <td class="table-actions">
                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#medicalModal<%= patient.getId() %>">Medical info</button>
                <button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#vitalsModal<%= patient.getId() %>">Vital signs</button>
            </td>
        </tr>

        <!-- Medical Modal -->
        <div class="modal fade" id="medicalModal<%= patient.getId() %>" tabindex="-1" aria-labelledby="medicalModalLabel<%= patient.getId() %>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="addMedicalInfo" method="post">
                        <input type="hidden" name="patientId" value="<%= patient.getId() %>">
                        <div class="modal-header">
                            <h5 class="modal-title" id="medicalModalLabel<%= patient.getId() %>">Medical info: <%= patient.getNom() %> <%= patient.getPrenom() %></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <textarea class="form-control mb-2" name="antecedents" placeholder="Medical history"></textarea>
                            <textarea class="form-control mb-2" name="allergies" placeholder="Allergies"></textarea>
                            <textarea class="form-control mb-2" name="traitements" placeholder="Current treatments"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-warning">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Vitals Modal -->
        <div class="modal fade" id="vitalsModal<%= patient.getId() %>" tabindex="-1" aria-labelledby="vitalsModalLabel<%= patient.getId() %>" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form action="addVitals" method="post">
                        <input type="hidden" name="patientId" value="<%= patient.getId() %>">
                        <div class="modal-header">
                            <h5 class="modal-title" id="vitalsModalLabel<%= patient.getId() %>">Vital signs: <%= patient.getNom() %> <%= patient.getPrenom() %></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="text" class="form-control mb-2" name="tension" placeholder="Blood pressure">
                            <input type="number" class="form-control mb-2" name="frequenceCardiaque" placeholder="Heart rate">
                            <input type="number" step="0.1" class="form-control mb-2" name="temperature" placeholder="Temperature">
                            <input type="number" class="form-control mb-2" name="frequenceResp" placeholder="Respiratory rate">
                            <input type="number" step="0.1" class="form-control mb-2" name="poids" placeholder="Weight">
                            <input type="number" step="0.01" class="form-control mb-2" name="taille" placeholder="Height">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-success">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
