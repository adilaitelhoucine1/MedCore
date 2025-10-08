<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Inscription - MedCore</title>
    <style>
        body {
            background: linear-gradient(135deg, #4ca1af, #c4e0e5);
            font-family: 'Segoe UI', Arial, sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
        }
        .container {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(44,62,80,0.15);
            padding: 40px 32px;
            width: 350px;
            text-align: center;
        }
        h2 {
            color: #4ca1af;
            margin-bottom: 24px;
            font-size: 1.7em;
        }
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0 16px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 1em;
        }
        .btn {
            width: 100%;
            padding: 12px 0;
            background: #4ca1af;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1.1em;
            cursor: pointer;
            font-weight: bold;
            margin-top: 8px;
            transition: background 0.2s;
        }
        .btn:hover {
            background: #357e8c;
        }
        .link {
            display: block;
            margin-top: 18px;
            color: #4ca1af;
            text-decoration: none;
        }
        .link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Inscription</h2>
    <form action="/MedCore_war_exploded/register" method="post">
        <input type="text" name="username" placeholder="Nom d'utilisateur" required>
        <input type="text" name="secondname" placeholder="Nom de famille" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Mot de passe" required>

        <label for="role">Rôle:</label>
        <select name="role" id="role" required>
            <option value="INFIRMIER">INFIRMIER</option>
            <option value="GENERALISTE">GENERALISTE</option>
            <option value="SPECIALISTE">SPECIALISTE</option>
        </select>

        <button type="submit" class="btn">S'inscrire</button>
    </form>
    <a href="login.html" class="link">Déjà inscrit ? Se connecter</a>
</div>
</body>
</html>