<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.medcore.service.SpecialisteService" %>
<%@ page import="com.example.medcore.dao.UserDAO" %>
<%@ page import="com.example.medcore.dao.ConsultationDAO" %>
<%@ page import="com.example.medcore.model.*" %>

<%
    SpecialisteService specialisteService = new SpecialisteService();
    UserDAO userDAO = new UserDAO();
    ConsultationDAO consultationDAO = new ConsultationDAO();


    int medecinNid = Integer.parseInt(request.getParameter("medecinId"));
    long consultationId = Long.parseLong(request.getParameter("consultationId"));
    Consultation consultation = consultationDAO.findByID(consultationId);

    MedecinSpecialiste medecinSpecialiste = (MedecinSpecialiste) userDAO.findById(medecinNid);
    String medecinNom = medecinSpecialiste.getNom();
    String medecinSpecialite = medecinSpecialiste.getSpecialite().toString();
    String consultationMotif = consultation.getMotif();
    String consultationDate = consultation.getDateConsultation().toString();

    List<Object[]> creneaux = (List<Object[]>) request.getAttribute("creneaux");
    response.getWriter().println("creneaux  size"+creneaux.size());

%>

<!DOCTYPE html>
<html>
<head>
    <title>Choisir Creneau </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .calendar {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
        }

        .creneau-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
            cursor: pointer;
        }

        .creneau-card.available {
            background-color: #d1e7dd;
        }

        .creneau-card.reserved {
            background-color: #f8d7da;
            cursor: not-allowed;
        }
    </style>
</head>
<body class="p-4">
<h3>Choisir un creneau pour <%= medecinNom %> - <%= medecinSpecialite %>
</h3>

<div class="mb-4">
    <h5>Details de la demande :</h5>
    <p><strong>Consultation :</strong> <%= consultationMotif %> - <%= consultationDate %>
    </p>
</div>

<form method="post" action="<%=request.getContextPath()%>/confirmerDemande">
    <input type="hidden" name="consultationId" value="<%=consultationId%>">
    <input type="hidden" name="medecinId" value="<%=medecinNid%>">

    <div class="calendar">
        <%


            if (creneaux != null && !creneaux.isEmpty()) {
                for (Object[] c : creneaux) {
                    DisponibiliteMedecin.Status status = (DisponibiliteMedecin.Status) c[0];
                    LocalDateTime dateDebut = (LocalDateTime) c[1];
                    LocalDateTime dateFin = (LocalDateTime) c[2];
                    Long creneauId = (Long) c[3];

                    String statusClass = status.equals(DisponibiliteMedecin.Status.DISPONIBLE) ? "available" : "reserved";
        %>
        <div class="creneau-card <%= statusClass %>">
            <% if (status.equals(DisponibiliteMedecin.Status.DISPONIBLE)) { %>
            <input type="radio" name="creneauId" value="<%= creneauId %>" required>
            <% } %>
            <p><strong><%= dateDebut %> - <%= dateFin %></strong></p>
            <p>Status: <%= status %></p>
        </div>
        <%
            }
        } else {
        %>
        <p>No creneaux available</p>
        <%
            }
        %>

    </div>


    <div class="mt-4">
        <label for="question" class="form-label"><strong>Question au specialiste :</strong></label>
        <textarea id="question" name="question" class="form-control" rows="3" placeholder="Votre question ici..."
                  required></textarea>
    </div>

    <div class="mt-3">
        <label for="priority" class="form-label"><strong>Priorite :</strong></label>
        <select id="priority" name="priority" class="form-select" required>
            <option value="">-- Selectionner la priorite --</option>
            <option value="NON_URGENTE">NON_Urgente</option>
            <option value="NORMALE">Normale</option>
            <option value="URGENTE">Urgente</option>
        </select>
    </div>


    <button type="submit" class="btn btn-success mt-4">Confirmer la demande</button>
</form>

</body>
</html>
