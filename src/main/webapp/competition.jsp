<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%!
    private String getCookieValue(HttpServletRequest request, String name, String defaultValue) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (name.equals(c.getName())) {
                    try {
                        return java.net.URLDecoder.decode(c.getValue(), "UTF-8");
                    } catch (Exception e) {
                        return defaultValue;
                    }
                }
            }
        }
        return defaultValue;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Соревнования по гребле</title>
    <style>
        <%
            String bgColor = getCookieValue(request, "page.color", "white");
            String userNameFromSession = (String) session.getAttribute("currentUser");
            Integer visitCountFromSession = (Integer) session.getAttribute("visitCount");
            String lastVisitFromSession = (String) session.getAttribute("lastVisit");
        %>
        body { 
            font-family: Arial, sans-serif; 
            margin: 20px; 
            background: <%= bgColor %>; 
            transition: background-color 0.3s;
        }
        .header { background: #2c3e50; color: white; padding: 15px; border-radius: 5px; display: flex; justify-content: space-between; align-items: center; }
        .user-info { font-size: 14px; }
        .user-info p { margin: 5px 0; }
        .session-info { background: #ecf0f1; padding: 15px; border-radius: 5px; margin: 20px 0; border-left: 4px solid #3498db; }
        .session-info h3 { margin-top: 0; color: #2c3e50; }
        .session-info p { margin: 5px 0; color: #555; }
        .preferences-btn { background: #27ae60; padding: 8px 15px; border-radius: 3px; text-decoration: none; color: white; font-size: 12px; display: inline-block; margin-right: 10px; }
        .preferences-btn:hover { background: #229954; }
        .logout-link { color: white; text-decoration: underline; cursor: pointer; font-size: 12px; }
        .logout-link:hover { text-decoration: none; }
        h1 { color: #2c3e50; margin: 20px 0; }
        .form-container { background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); max-width: 600px; margin-bottom: 20px; }
        form { display: flex; flex-direction: column; gap: 10px; }
        input, select { padding: 8px; border: 1px solid #bdc3c7; border-radius: 3px; }
        button { padding: 10px; background: #3498db; color: white; border: none; border-radius: 3px; cursor: pointer; font-weight: bold; }
        button:hover { background: #2980b9; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-top: 20px; }
        th { background-color: #3498db; color: white; padding: 12px; text-align: left; }
        td { padding: 10px; border: 1px solid #ddd; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .delete-btn { background: #e74c3c; padding: 5px 10px; margin: 0; font-size: 12px; }
        .delete-btn:hover { background: #c0392b; }
    </style>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");
        
        if (session.getAttribute("visitCount") == null) {
            session.setAttribute("visitCount", 1);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            session.setAttribute("lastVisit", sdf.format(new java.util.Date()));
        }
    %>
    
    <div class="header">
        <div>
            <h1 style="margin: 0; color: white;">Результаты соревнований по гребле</h1>
        </div>
        <div class="user-info">
            <a href="preferences.jsp" class="preferences-btn">⚙️ Параметры</a>
            <a href="invalidate_session.jsp" class="logout-link">Очистить сессию</a>
        </div>
    </div>
    
    <div class="session-info">
        <h3>Информация о вашем визите</h3>
        <p><strong>Имя:</strong> <%= userNameFromSession != null ? userNameFromSession : "Не установлено" %></p>
        <p><strong>Посещений:</strong> <%= visitCountFromSession != null ? visitCountFromSession : 0 %></p>
        <p><strong>Последнее:</strong> <%= lastVisitFromSession != null ? lastVisitFromSession : "Первый визит" %></p>
        <p><strong>Цвет фона:</strong> <%= bgColor %></p>
        <p style="font-size: 12px; color: #999;">🆔 ID сессии: <%= session.getId() %></p>
    </div>
    
    <%
        HttpSession httpSession = request.getSession();
        
        @SuppressWarnings("unchecked")
        ArrayList<Map<String, String>> results = (ArrayList<Map<String, String>>) httpSession.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            Map<String, String> example = new HashMap<>();
            example.put("date", "2025-11-19");
            example.put("distance", "1000");
            example.put("time", "3:45.20");
            example.put("athlete", "Иванов И.И.");
            results.add(example);
            httpSession.setAttribute("results", results);
        }
        
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String date = request.getParameter("date");
            String distance = request.getParameter("distance");
            String time = request.getParameter("time");
            String athlete = request.getParameter("athlete");
            
            if (date != null && !date.isEmpty() && distance != null && !distance.isEmpty() && 
                time != null && !time.isEmpty() && athlete != null && !athlete.isEmpty()) {
                Map<String, String> record = new HashMap<>();
                record.put("date", date);
                record.put("distance", distance);
                record.put("time", time);
                record.put("athlete", athlete);
                results.add(record);
            }
        }
        
        if ("delete".equals(action)) {
            try {
                int index = Integer.parseInt(request.getParameter("index"));
                if (index >= 0 && index < results.size()) {
                    results.remove(index);
                }
            } catch (NumberFormatException e) {
            }
        }
    %>
    
    <div class="form-container">
        <h2>Добавить результат</h2>
        <form method="POST">
            <label for="athlete">Имя спортсмена:</label>
            <input type="text" id="athlete" name="athlete" placeholder="Иванов И.И." required>
            
            <label for="date">Дата:</label>
            <input type="date" id="date" name="date" required>
            
            <label for="distance">Дистанция (м):</label>
            <select id="distance" name="distance" required>
                <option value="">-- Выберите --</option>
                <option value="500">500 м</option>
                <option value="1000">1000 м</option>
                <option value="2000">2000 м</option>
                <option value="5000">5000 м</option>
            </select>
            
            <label for="time">Время (мм:сс.сс):</label>
            <input type="text" id="time" name="time" placeholder="3:45.20" pattern="\d{1,2}:\d{2}\.\d{2}" required>
            
            <input type="hidden" name="action" value="add">
            <button type="submit">Добавить</button>
        </form>
    </div>
    
    <h2>Таблица результатов</h2>
    <% if (results.size() > 0) { %>
        <table>
            <tr>
                <th>Спортсмен</th>
                <th>Дата</th>
                <th>Дистанция</th>
                <th>Время</th>
                <th>Действие</th>
            </tr>
            <% 
                for (int i = 0; i < results.size(); i++) {
                    Map<String, String> record = results.get(i);
            %>
            <tr>
                <td><%= record.get("athlete") %></td>
                <td><%= record.get("date") %></td>
                <td><%= record.get("distance") %> м</td>
                <td><%= record.get("time") %></td>
                <td>
                    <form method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="index" value="<%= i %>">
                        <button type="submit" class="delete-btn">Удалить</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>
