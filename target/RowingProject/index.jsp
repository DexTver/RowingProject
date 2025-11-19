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
            border-radius: 5px; 
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
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Система соревнований по гребле</h1>
        
        <div class="info-box">
            <h3>Добро пожаловать!</h3>
            <p>Приложение для управления результатами соревнований по гребле.</p>
        </div>
        
        <div class="button-group">
            <a href="competition.jsp">Войти в приложение</a>
        </div>
    </div>
</body>
</html>
