<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.example.medcore.model.*" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord Specialiste</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4">
    <h2 class="mb-4">Tableau de bord du Specialiste</h2>

    <!-- Section Profil -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">Informations du profil</div>
        <div class="card-body">
            <%
                MedecinSpecialiste user =(MedecinSpecialiste) session.getAttribute("user");
            %>
            <p><strong>Nom :</strong> <%= user != null ? user.getNom() : "Inconnu" %></p>
            <p><strong>Specialite :</strong> <%= user.getSpecialite() != null ? user.getSpecialite() : "Non specifiee" %></p>
            <p><strong>Tarif :</strong> <%= user.getTarif() != null ? user.getTarif() + " MAD / consultation" : "Non specifiee" %></p>
            <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editProfileModal">Modifier le profil</button>
        </div>
    </div>

    <!-- Section Disponibilites -->
    <div class="card mb-4">
        <div class="card-header bg-success text-white">Creneaux disponibles </div>
        <div class="card-body">
            <table class="table table-bordered text-center align-middle">
                <thead>
                <tr>
<%--                    <th>Date</th>--%>
                    <th>Heure</th>
                    <th>Statut</th>
<%--                    <th>Action</th>--%>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Object[]> creneaus = (List<Object[]>) request.getAttribute("creneaus");

                    if (creneaus != null && !creneaus.isEmpty()) {
                        for (Object[] row : creneaus) {
                            DisponibiliteMedecin.Status status = ( DisponibiliteMedecin.Status) row[0];
                            String statusString = status.name();
                            LocalDateTime dateDebut = (LocalDateTime) row[1];
                            LocalDateTime dateFin = (LocalDateTime) row[2];

                            String badgeClass = "";
                            String label = "";

                            switch (statusString) {
                                case "DISPONIBLE":
                                    badgeClass = "bg-success";
                                    label = "Disponible";
                                    break;
                                case "RESERVE":
                                    badgeClass = "bg-warning text-dark";
                                    label = "Reserve";
                                    break;
                                case "ARCHIVE":
                                    badgeClass = "bg-secondary";
                                    label = "Archive";
                                    break;
                            }

                            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                %>
                <tr>
                    <td><%= dateDebut.format(timeFormatter) %> - <%= dateFin.format(timeFormatter) %></td>
                    <td><span class="badge <%= badgeClass %>"><%= label %></span></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="3" class="text-center text-muted">Aucun créneau disponible</td>
                </tr>
                <%
                    }
                %>

                </tbody>


            </table>

        </div>
    </div>

    <!-- Section Demandes d'expertise -->
    <div class="card mb-4">
        <div class="card-header bg-info text-white">Demandes d'expertise</div>
        <div class="card-body">
            <div class="mb-3">
                <input type="text" class="form-control" placeholder="Rechercher par patient ou statut...">
            </div>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID Demande</th>
                    <th>Patient</th>
                    <th>Demande par</th>
                    <th>Date</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>#EX123</td>
                    <td>Jane Doe</td>
                    <td>Dr. Ahmed</td>
                    <td>2025-10-12</td>
                    <td><span class="badge bg-warning text-dark">En attente</span></td>
                    <td><button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#respondModal">Repondre</button></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Modifier Profil -->
<div class="modal fade" id="editProfileModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modifier le profil</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form action="<%= request.getContextPath() %>/updateSpecialiteProfiile" method="post">
                    <div class="mb-3">
                        <input type="hidden" name="user_id" value="<%=user.getId()%>">
                        <label class="form-label">Nom</label>
                        <input type="text" name="name" class="form-control" value="adil">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Specialite</label>
                        <select name="specialite" class="form-control">
                            <option value="CARDIOLOGIE">CARDIOLOGIE</option>
                            <option value="PNEUMOLOGIE">PNEUMOLOGIE</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Tarif (MAD)</label>
                        <input type="number" name="tarif" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label">DureeConsultation (min)</label>
                        <input type="number" name="dureeConsultation" class="form-control"
                               value="<%= user.getDureeConsultation() != null ? user.getDureeConsultation() : 0 %>">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                        <button type="submit" class="btn btn-primary">Sauvegarder</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<!-- Modal Repondre -->
<div class="modal fade" id="respondModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Repondre a la demande</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="mb-3">
                        <label class="form-label">Reponse</label>
                        <textarea class="form-control" rows="4" placeholder="Entrer votre reponse ici..."></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                <button type="button" class="btn btn-primary">Envoyer</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
