<%@ page import="com.example.medcore.model.Patient" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil Infirmier - Demo</title>
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
<a href="#" class="btn btn-outline-danger logout-btn">Déconnexion</a>
<div class="container">
    <h2 class="dashboard-title text-center">Bienvenue, Adilaitelhoucine (Infirmier)</h2>

    <div class="row mb-4">
        <div class="col-12">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#patientModal">
                ➕ Créer un nouveau patient
            </button>
        </div>
    </div>

    <!-- Modal: Créer un nouveau patient -->
    <div class="modal fade" id="patientModal" tabindex="-1" aria-labelledby="patientModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="registerPatient" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="patientModalLabel">Informations administratives</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control mb-2" id="nom" name="nom" placeholder="Nom">
                        <input type="text" class="form-control mb-2" id="prenom" name="prenom" placeholder="Prénom">
                        <input type="date" class="form-control mb-2" id="dateNaissance" name="dateNaissance" placeholder="Date de naissance">
                        <input type="text" class="form-control mb-2" id="numSecu" name="numSecu" placeholder="Numéro de Sécurité Sociale">
                        <input type="text" class="form-control mb-2" id="adresse" name="adresse"  placeholder="Adresse">
                        <input type="tel" class="form-control mb-2" id="telephone" name="telephone" placeholder="Téléphone">
                        <input type="text" class="form-control mb-2"  id="mutuelle" name="mutuelle" placeholder="Mutuelle">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <h3 class="mt-5 mb-3 text-primary">Liste des patients</h3>
    <table class="table table-striped table-bordered align-middle">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Date de naissance</th>
            <th>Numero Securite Sociale</th>
            <th>Telephone</th>
            <th>Mutuelle</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Patient> patients = (List<Patient>) request.getAttribute("patients");




            if(patients !=null){
                System.out.println("SIIIIIIIIIIIIIZE   "+patients.size());
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
                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#medicalModal<%= patient.getId() %>"> Recueil medical</button>
                <button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#vitalsModal<%= patient.getId() %>"> Signes vitaux</button>
            </td>
        </tr>
        <%
                }
            }
        %>

        </tbody>
    </table>

    <!-- MODALS FOR EACH PATIENT -->
    <!-- Medical Data Modal 1 -->
    <div class="modal fade" id="medicalModal1" tabindex="-1" aria-labelledby="medicalModalLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="medicalModalLabel1">Recueil médical: Dupont Marie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <textarea class="form-control mb-2" placeholder="Antécédents médicaux"></textarea>
                        <textarea class="form-control mb-2" placeholder="Allergies"></textarea>
                        <textarea class="form-control mb-2" placeholder="Traitements en cours"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-warning">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Vital Signs Modal 1 -->
    <div class="modal fade" id="vitalsModal1" tabindex="-1" aria-labelledby="vitalsModalLabel1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="vitalsModalLabel1">Signes vitaux: Dupont Marie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control mb-2" placeholder="Tension artérielle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence cardiaque">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Température corporelle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence respiratoire">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Poids">
                        <input type="number" step="0.01" class="form-control mb-2" placeholder="Taille">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-success">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Medical Data Modal 2 -->
    <div class="modal fade" id="medicalModal2" tabindex="-1" aria-labelledby="medicalModalLabel2" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="medicalModalLabel2">Recueil médical: Durand Paul</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <textarea class="form-control mb-2" placeholder="Antécédents médicaux"></textarea>
                        <textarea class="form-control mb-2" placeholder="Allergies"></textarea>
                        <textarea class="form-control mb-2" placeholder="Traitements en cours"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-warning">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Vital Signs Modal 2 -->
    <div class="modal fade" id="vitalsModal2" tabindex="-1" aria-labelledby="vitalsModalLabel2" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="vitalsModalLabel2">Signes vitaux: Durand Paul</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control mb-2" placeholder="Tension artérielle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence cardiaque">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Température corporelle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence respiratoire">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Poids">
                        <input type="number" step="0.01" class="form-control mb-2" placeholder="Taille">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-success">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Medical Data Modal 3 -->
    <div class="modal fade" id="medicalModal3" tabindex="-1" aria-labelledby="medicalModalLabel3" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="medicalModalLabel3">Recueil médical: Martin Sophie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <textarea class="form-control mb-2" placeholder="Antécédents médicaux"></textarea>
                        <textarea class="form-control mb-2" placeholder="Allergies"></textarea>
                        <textarea class="form-control mb-2" placeholder="Traitements en cours"></textarea>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-warning">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- Vital Signs Modal 3 -->
    <div class="modal fade" id="vitalsModal3" tabindex="-1" aria-labelledby="vitalsModalLabel3" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form>
                    <div class="modal-header">
                        <h5 class="modal-title" id="vitalsModalLabel3">Signes vitaux: Martin Sophie</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <input type="text" class="form-control mb-2" placeholder="Tension artérielle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence cardiaque">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Température corporelle">
                        <input type="number" class="form-control mb-2" placeholder="Fréquence respiratoire">
                        <input type="number" step="0.1" class="form-control mb-2" placeholder="Poids">
                        <input type="number" step="0.01" class="form-control mb-2" placeholder="Taille">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="button" class="btn btn-success">Enregistrer</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>