<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ошибка</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #fadbd8; }
        .error-container { background: #f5b7b1; padding: 20px; border-radius: 5px; max-width: 600px; border: 2px solid #e74c3c; }
        h1 { color: #c0392b; }
        .error-details { background: white; padding: 10px; border-radius: 3px; margin-top: 10px; }
        a { color: #2980b9; text-decoration: none; }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>⚠️ Произошла ошибка</h1>
        <p>К сожалению, при обработке вашего запроса произошла ошибка.</p>
        
        <div class="error-details">
            <p><strong>Тип ошибки:</strong> <%= exception.getClass().getName() %></p>
            <p><strong>Сообщение:</strong> <%= exception.getMessage() %></p>
        </div>
        
        <p style="margin-top: 20px;">
            <a href="index.jsp">← На главную страницу</a>
        </p>
    </div>
</body>
</html>
