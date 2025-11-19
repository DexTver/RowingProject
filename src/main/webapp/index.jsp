<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Система соревнований по гребле</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            max-width: 400px;
            width: 100%;
            text-align: center;
        }
        h1 {
            color: #2c3e50;
            margin-top: 0;
        }
        p {
            color: #555;
            margin: 20px 0;
        }
        .info-box {
            background: #ecf0f1;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border-left: 4px solid #3498db;
            text-align: center;
        }
        .info-box h3 {
            margin-top: 0;
            color: #2c3e50;
        }
        .button-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 20px;
        }
        a {
            padding: 15px 20px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
            transition: background 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 50px;
        }
        a:hover {
            background: #2980b9;
        }
        .admin { background: #e74c3c; }
        .admin:hover { background: #c0392b; }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Система соревнований по гребле</h1>

        <div class="info-box">
            <h3>Информация о системе</h3>
            <p>Для доступа к системе соревнований используйте учетные данные, которые вам предоставлены администратором.</p>
        </div>

        <p style="font-size: 14px; color: #666;">Выберите роль для входа:</p>

        <div class="button-group">
            <a href="competition.jsp" class="admin">Войти как администратор</a>
            <a href="competition.jsp">Войти как пользователь</a>
        </div>

        <p style="font-size: 12px; color: #999; margin-top: 20px;">
            Примечание: Браузер попросит ввести логин и пароль при нажатии на кнопку входа.
        </p>
    </div>
</body>
</html>
