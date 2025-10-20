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

<body class="bg-light">

<a href="<%= request.getContextPath()%>/logout" class="btn btn-outline-danger logout-btn">Deconnexion</a>

<div class="container mt-4">
    <h2 class="mb-4">Tableau de bord du Specialiste</h2>

    <!-- Section Profil -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">Informations du profil</div>
        <div class="card-body">
            <%
                MedecinSpecialiste user = (MedecinSpecialiste) session.getAttribute("user");
            %>

            <p><strong>Nom :</strong> <%= user != null ? user.getNom() : "Inconnu" %></p>
            <p><strong>Specialite :</strong> <%= user.getSpecialite() != null ? user.getSpecialite() : "Non specifiee" %></p>
            <p><strong>Tarif :</strong> <%= user.getTarif() != null ? user.getTarif() + " MAD / consultation" : "Non specifie" %></p>

            <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                Modifier le profil
            </button>
        </div>
    </div>

    <!-- Section Disponibilites -->
    <div class="card mb-4">
        <div class="card-header bg-success text-white">Creneaux disponibles</div>
        <div class="card-body">
            <table class="table table-bordered text-center align-middle">
                <thead>
                <tr>
                    <th>Heure</th>
                    <th>Statut</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Object[]> creneaus = (List<Object[]>) request.getAttribute("creneaus");
                    if (creneaus != null && !creneaus.isEmpty()) {
                        for (Object[] row : creneaus) {
                            DisponibiliteMedecin.Status status = (DisponibiliteMedecin.Status) row[0];
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
                    <td colspan="3" class="text-center text-muted">Aucun creneau disponible</td>
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
                    <th>Priorite</th>
                    <th>Date</th>
                    <th>Statut</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Object[]> Demandes = (List<Object[]>) request.getAttribute("Demandes");
                    if (Demandes != null && !Demandes.isEmpty()) {
                        for (Object[] d : Demandes) {
                            long id = (Long) d[0];
                            DemandeExpertise.Priorite priorite = (DemandeExpertise.Priorite) d[1];
                            DemandeExpertise.Status status = (DemandeExpertise.Status) d[2];
                            LocalDateTime dateDemande = (LocalDateTime) d[3];
                            String patient_name = (String) d[4];
                            String patient_prenom = (String) d[5];
                            Long consutation_id = (Long) d[6];

                            String badgeperiorite = "";
                            switch (priorite.name()) {
                                case "NON_URGENTE":
                                    badgeperiorite = "bg-success text-white rounded-pill px-3 py-1 shadow-sm";
                                    break;
                                case "NORMALE":
                                    badgeperiorite = "bg-warning text-dark rounded-pill px-3 py-1 shadow-sm";
                                    break;
                                case "URGENTE":
                                    badgeperiorite = "bg-danger text-white rounded-pill px-3 py-1 shadow-sm";
                                    break;
                            }
                %>
                <tr>
                    <td><%= id %></td>
                    <td><%= patient_name + " " + patient_prenom %></td>
                    <td><span class="<%= badgeperiorite %> small"><%= priorite.name() %></span></td>
                    <td><%= dateDemande %></td>
                    <td><span class="badge bg-warning text-dark"><%= status.name() %></span></td>
                    <td>
                        <%
                            if(status.name().equals("TERMINEE")){


                        %>
                        <button
                                type="button"
                                class="btn btn-sm text-white  px-3 shadow-sm"
                                style="background: linear-gradient(90deg, #007b0f, #00b448); border: none;"
                                data-bs-toggle="modal"
                                data-bs-target="#Actemodal<%= consutation_id %>">
                            <i class="bi bi-plus-circle me-1"></i> Ajouter Acte
                        </button>

                        <%
                            }
                        %>
                        <!-- Button to open modal -->
                        <button
                                type="button"
                                class="btn btn-sm btn-primary"
                                data-bs-toggle="modal"
                                data-bs-target="#respondModal<%=id%>">
                            Repondre
                        </button>

                        <!-- Form for Annuler / Delete action -->
                        <form action="/yourCancelServlet" method="post" style="display:inline;">
                            <input type="hidden" name="demandeId" value="<%= id %>">
                            <button type="submit" class="btn btn-sm btn-danger">
                                Annuler
                            </button>
                        </form>
                    </td>

                </tr>


                <div class="modal fade" id="respondModal<%=id%>" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Repondre a la demande</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <form action="<%=request.getContextPath()%>/updatedemande" method="post">
                                    <input type="hidden" name="demandeId" value="<%=id%>">
                                    <div class="mb-3">
                                        <label class="form-label">Avis Medecin</label>
                                        <textarea name="avis_medecin" class="form-control" rows="4" placeholder="Entrez votre reponse ici..."></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Recommandations</label>
                                        <textarea name="recommandations" class="form-control" rows="4" placeholder="Entrez votre reponse ici..."></textarea>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                        <button type="submit" class="btn btn-primary">Envoyer</button>
                                    </div>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>

                <!-- Modal for adding Acte -->
                <div class="modal fade" id="Actemodal<%= consutation_id %>" tabindex="-1" aria-labelledby="respondModalLabel<%= id %>" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content shadow-lg rounded-4">
                            <div class="modal-header bg-primary text-white">
                                <h5 class="modal-title" id="respondModalLabel<%= id %>">
                                    Ajouter un acte technique  Demande #<%= id %>
                                </h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                            </div>

                            <form action="<%=request.getContextPath()%>/addactetechnique" method="post">
                                <input type="hidden" name="consutation_id" value="<%=consutation_id%>">
                                <div class="modal-body">
                                    <!-- You will pass the demande or consultation ID -->
                                    <input type="hidden" name="demande_id" value="<%= id %>">

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Type d acte</label>
                                        <select name="actetyppe" class="form-select" required>
                                            <option value="">Selectionner un acte</option>
                                            <option value="RADIOGRAPHIE">Radiographie</option>
                                            <option value="ECHOGRAPHIE">echographie</option>
                                            <option value="IRM">IRM</option>
                                            <option value="ELECTROCARDIOGRAMME">electrocardiogramme</option>
                                            <option value="LASER_DERMATO">Laser dermatologique</option>
                                            <option value="FOND_OEIL">Fond d œil</option>
                                            <option value="ANALYSE_SANG">Analyse de sang</option>
                                            <option value="ANALYSE_URINE">Analyse d urine</option>
                                        </select>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label fw-semibold">Resultat</label>
                                        <textarea name="result" class="form-control" rows="3" placeholder="Saisir le resultat..." required></textarea>
                                    </div>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Annuler</button>
                                    <button type="submit" class="btn btn-success">Ajouter</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>




                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6" class="text-center text-muted">Aucune demande disponible</td>
                </tr>
                <%
                    }
                %>
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
                    <input type="hidden" name="user_id" value="<%= user.getId() %>">

                    <div class="mb-3">
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
                        <label class="form-label">Duree Consultation (min)</label>
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


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
