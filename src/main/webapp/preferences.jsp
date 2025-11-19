<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Предпочтения</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        .container { background: white; padding: 30px; border-radius: 10px; max-width: 500px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin: 0 auto; }
        h1 { color: #2c3e50; text-align: center; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #2c3e50; }
        input[type="text"],
        select { width: 100%; padding: 10px; border: 1px solid #bdc3c7; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 12px; background: #3498db; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; font-weight: bold; }
        button:hover { background: #2980b9; }
        .info { background: #ecf0f1; padding: 10px; border-radius: 5px; margin-bottom: 20px; font-size: 14px; }
        a { color: #3498db; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Предпочтения</h1>
        
        <div class="info">
            Установите ваше имя и цвет фона страницы.
        </div>
        
        <form method="POST" action="PreferencesProcessor">
            <div class="form-group">
                <label for="username">Ваше имя:</label>
                <%
                    Cookie[] cookies = request.getCookies();
                    String savedUsername = "";
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            if ("user.name".equals(c.getName())) {
                                savedUsername = java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                                break;
                            }
                        }
                    }
                %>
                <input type="text" id="username" name="username" 
                       value="<%= savedUsername %>" placeholder="Введите имя" required>
            </div>
            
            <div class="form-group">
                <label for="bgcolor">Цвет фона:</label>
                <%
                    String savedColor = "white";
                    if (cookies != null) {
                        for (Cookie c : cookies) {
                            if ("page.color".equals(c.getName())) {
                                savedColor = c.getValue();
                                break;
                            }
                        }
                    }
                %>
                <select id="bgcolor" name="bgcolor">
                    <option value="white" <%= "white".equals(savedColor) ? "selected" : "" %>>Белый</option>
                    <option value="#e8f4f8" <%= "#e8f4f8".equals(savedColor) ? "selected" : "" %>>Голубой</option>
                    <option value="#f0f8e8" <%= "#f0f8e8".equals(savedColor) ? "selected" : "" %>>Зелёный</option>
                    <option value="#fff8e8" <%= "#fff8e8".equals(savedColor) ? "selected" : "" %>>Жёлтый</option>
                    <option value="#ffe8f0" <%= "#ffe8f0".equals(savedColor) ? "selected" : "" %>>Розовый</option>
                </select>
            </div>
            
            <button type="submit">Сохранить</button>
        </form>
        
        <p style="text-align: center; margin-top: 20px;">
            <a href="competition.jsp">← Вернуться</a>
        </p>
    </div>
</body>
</html>
