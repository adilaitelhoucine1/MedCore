<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    com.example.medcore.model.Utilisateur user = (com.example.medcore.model.Utilisateur) session.getAttribute("user");
    if (user == null || !"INFIRMIER".equals(user.getRole().toString())) {
        response.sendRedirect("infirmier-login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Accueil Infirmier</title>
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
            letter-spacing: 1px;
            text-shadow: 0 2px 12px #a2c5f9;
        }
        .logout-btn {
            position: absolute;
            top: 18px;
            right: 28px;
        }
        .card-custom {
            border-radius: 18px;
            box-shadow: 0 4px 18px #bdd8fa7a;
            transition: box-shadow 0.2s;
        }
        .card-custom:hover {
            box-shadow: 0 8px 30px #1677ff33;
        }
        .card-title {
            font-size: 1.3rem;
            font-weight: 700;
            color: #1677ff;
            text-shadow: 0 1px 8px #e3f1ff;
        }
        .btn-primary, .btn-info, .btn-success, .btn-warning {
            border-radius: 22px;
            font-weight: 600;
            padding: 8px 22px;
            box-shadow: 0 2px 8px #bdd8fa44;
            transition: background 0.15s;
        }
        .btn-primary:hover, .btn-info:hover, .btn-success:hover, .btn-warning:hover {
            background: #1677ff !important;
            color: #fff !important;
        }
        .form-control {
            border-radius: 14px;
        }
        .dashboard-row {
            margin-top: 45px;
        }
        @media (max-width: 970px) {
            .dashboard-row {
                flex-direction: column;
                gap: 32px !important;
            }
        }
    </style>
</head>
<body>
<a href="logout" class="btn btn-outline-danger logout-btn">Déconnexion</a>
<div class="container">
    <h2 class="dashboard-title text-center">Bienvenue, <%= user.getNom() %> (Infirmier)</h2>
    <div class="row dashboard-row d-flex justify-content-center gap-3">
        <!-- Accueil du patient -->
        <div class="col-md-6">
            <div class="card card-custom p-4">
                <div class="card-title mb-3">Accueil du patient</div>
                <form action="searchPatient" method="get" class="mb-3">
                    <div class="input-group">
                        <input type="text" class="form-control" name="searchQuery" placeholder="Nom ou ID du patient" required>
                        <button class="btn btn-info" type="submit">🔍 Rechercher</button>
                    </div>
                </form>
                <div class="d-flex flex-column gap-2 mb-2">
                    <!-- Button triggers modal -->
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#patientModal">
                        ➕ Créer un nouveau patient
                    </button>
                    <a href="enterVitalSigns.jsp" class="btn btn-success">💉 Saisir les signes vitaux</a>
                </div>
                <form action="addToQueue" method="post" class="mt-2">
                    <div class="input-group">
                        <input type="text" class="form-control" name="patientId" placeholder="ID du patient" required>
                        <button class="btn btn-warning" type="submit">⏳ Ajouter à la file d’attente</button>
                    </div>
                </form>
            </div>
        </div>
        <!-- Liste des patients -->
        <div class="col-md-5">
            <div class="card card-custom p-4">
                <div class="card-title mb-3">Liste des patients</div>
                <a href="patientList.jsp" class="btn btn-primary mb-3">🗂️ Voir la liste des patients</a>
                <form action="patientList.jsp" method="get" class="mb-2">
                    <label for="filterDate" class="form-label">Filtrer par date d’enregistrement:</label>
                    <div class="input-group">
                        <input type="date" class="form-control" id="filterDate" name="registerDate">
                        <button class="btn btn-info" type="submit">Filtrer</button>
                    </div>
                </form>
                <form action="patientList.jsp" method="get">
                    <label for="sortTime" class="form-label">Trier par heure d’arrivée:</label>
                    <div class="input-group">
                        <select class="form-select" id="sortTime" name="sortOrder">
                            <option value="asc">↑ Croissant</option>
                            <option value="desc">↓ Décroissant</option>
                        </select>
                        <button class="btn btn-success" type="submit">Trier</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal for creating patient -->
<div class="modal fade" id="patientModal" tabindex="-1" aria-labelledby="patientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="registerPatient" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="patientModalLabel">Informations du patient</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="nom" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="nom" name="nom" required>
                    </div>
                    <div class="mb-3">
                        <label for="prenom" class="form-label">Prénom</label>
                        <input type="text" class="form-control" id="prenom" name="prenom" required>
                    </div>
                    <div class="mb-3">
                        <label for="dateNaissance" class="form-label">Date de naissance</label>
                        <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" required>
                    </div>
                    <div class="mb-3">
                        <label for="numSecu" class="form-label">Numéro de Sécurité Sociale</label>
                        <input type="text" class="form-control" id="numSecu" name="numSecu" required>
                    </div>
                    <div class="mb-3">
                        <label for="adresse" class="form-label">Adresse</label>
                        <input type="text" class="form-control" id="adresse" name="adresse" required>
                    </div>
                    <div class="mb-3">
                        <label for="telephone" class="form-label">Téléphone</label>
                        <input type="tel" class="form-control" id="telephone" name="telephone" required>
                    </div>
                    <div class="mb-3">
                        <label for="mutuelle" class="form-label">Mutuelle</label>
                        <input type="text" class="form-control" id="mutuelle" name="mutuelle">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>