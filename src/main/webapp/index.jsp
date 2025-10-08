<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome - MedCore</title>
    <style>
        body {
            background: linear-gradient(135deg, #4ca1af, #c4e0e5);
            font-family: 'Segoe UI', Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 32px rgba(44, 62, 80, 0.15);
            padding: 40px 32px;
            width: 350px;
            text-align: center;
        }
        h1 {
            color: #4ca1af;
            margin-bottom: 24px;
            font-size: 2em;
        }
        .btn {
            display: block;
            width: 100%;
            margin: 12px 0;
            padding: 12px 0;
            background: #4ca1af;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 1.1em;
            cursor: pointer;
            transition: background 0.2s;
            font-weight: bold;
            text-decoration: none;
        }
        .btn:hover {
            background: #357e8c;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenue sur MedCore</h1>
    <a href="login.jsp" class="btn">Connexion</a>
    <a href="register.jsp" class="btn">Inscription</a>
</div>
</body>
</html>