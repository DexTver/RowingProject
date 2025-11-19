<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Соревнования по гребле</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f5f5f5; }
        .header { background: #2c3e50; color: white; padding: 15px; border-radius: 5px; display: flex; justify-content: space-between; align-items: center; }
        .user-info { font-size: 14px; }
        h1 { color: #2c3e50; }
        .form-container { background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); max-width: 600px; margin-bottom: 20px; }
        form { display: flex; flex-direction: column; gap: 10px; }
        input, select { padding: 8px; border: 1px solid #bdc3c7; border-radius: 3px; }
        button { padding: 10px; background: #3498db; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background: #2980b9; }
        table { border-collapse: collapse; width: 100%; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th { background-color: #3498db; color: white; padding: 12px; text-align: left; }
        td { padding: 10px; border: 1px solid #ddd; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .delete-btn { background: #e74c3c; padding: 5px 10px; }
        .delete-btn:hover { background: #c0392b; }
        .logout-btn { background: #e74c3c; }
        .logout-btn:hover { background: #c0392b; }
        .readonly { background: #ecf0f1; }
    </style>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");

        String username = request.getRemoteUser();
        boolean isAdmin = request.isUserInRole("admin");

        if (username == null) {
            response.sendRedirect("index.jsp");
            return;
        }
    %>

    <div class="header">
        <div>
            <h1 style="margin: 0; color: white;">Результаты соревнований по гребле</h1>
        </div>
        <div class="user-info">
            <p style="margin: 0;">Вы вошли как: <strong><%= username %></strong></p>
            <p style="margin: 5px 0 0 0;">Роль: <strong><%= isAdmin ? "Администратор" : "Пользователь" %></strong></p>
            <a href="javascript:void(0);" onclick="logout();" style="color: white; text-decoration: underline; font-size: 12px;">Выход</a>
        </div>
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
        if ("add".equals(action) && isAdmin) {
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

        // Обработка удаления результата (только для админов)
        if ("delete".equals(action) && isAdmin) {
            int index = Integer.parseInt(request.getParameter("index"));
            if (index >= 0 && index < results.size()) {
                results.remove(index);
            }
        }
    %>

    <% if (isAdmin) { %>
    <div class="form-container">
        <h2>Добавить результат соревнования</h2>
        <form method="POST">
            <label for="athlete">Имя спортсмена:</label>
            <input type="text" id="athlete" name="athlete" placeholder="Иванов И.И." required>

            <label for="date">Дата соревнования:</label>
            <input type="date" id="date" name="date" required>

            <label for="distance">Дистанция (м):</label>
            <select id="distance" name="distance" required>
                <option value="">-- Выберите дистанцию --</option>
                <option value="500">500 м</option>
                <option value="1000">1000 м</option>
                <option value="2000">2000 м</option>
                <option value="5000">5000 м</option>
            </select>

            <label for="time">Время (мм:сс.сс):</label>
            <input type="text" id="time" name="time" placeholder="3:45.20" pattern="\d{1,2}:\d{2}\.\d{2}" required>

            <input type="hidden" name="action" value="add">
            <button type="submit">Добавить результат</button>
        </form>
    </div>
    <% } %>

    <h2>Таблица результатов</h2>
    <% if (results.size() > 0) { %>
        <table>
            <tr>
                <th>Спортсмен</th>
                <th>Дата соревнования</th>
                <th>Дистанция</th>
                <th>Время</th>
                <% if (isAdmin) { %><th>Действие</th><% } %>
            </tr>
            <%
                for (int i = 0; i < results.size(); i++) {
                    Map<String, String> record = results.get(i);
            %>
            <tr>
                <td><%= record.get("athlete") != null ? record.get("athlete") : "Неизвестно" %></td>
                <td><%= record.get("date") %></td>
                <td><%= record.get("distance") %> м</td>
                <td><%= record.get("time") %></td>
                <% if (isAdmin) { %>
                <td>
                    <form method="POST" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="index" value="<%= i %>">
                        <button type="submit" class="delete-btn">Удалить</button>
                    </form>
                </td>
                <% } %>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Нет сохраненных результатов.</p>
    <% } %>

    <script>
        function logout() {
            if (confirm("Вы уверены, что хотите выйти?")) {
                window.location.href = "index.jsp";
                document.location = "/";
            }
        }
    </script>
</body>
</html>
