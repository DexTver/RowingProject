<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Соревнования по гребле</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #2c3e50; }
        .form-container { background: #ecf0f1; padding: 20px; border-radius: 5px; max-width: 600px; }
        form { display: flex; flex-direction: column; gap: 10px; }
        input, select { padding: 8px; border: 1px solid #bdc3c7; border-radius: 3px; }
        button { padding: 10px; background: #3498db; color: white; border: none; border-radius: 3px; cursor: pointer; }
        button:hover { background: #2980b9; }
        table { border-collapse: collapse; width: 100%; margin-top: 30px; }
        th { background-color: #3498db; color: white; padding: 12px; text-align: left; }
        td { padding: 10px; border: 1px solid #ddd; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        .delete-btn { background: #e74c3c; padding: 5px 10px; color: white; border: none; border-radius: 3px; cursor: pointer; }
        .delete-btn:hover { background: #c0392b; }
    </style>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();

        @SuppressWarnings("unchecked")
        ArrayList<Map<String, String>> results = (ArrayList<Map<String, String>>) httpSession.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            httpSession.setAttribute("results", results);
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String date = request.getParameter("date");
            String distance = request.getParameter("distance");
            String time = request.getParameter("time");

            if (date != null && !date.isEmpty() && distance != null && !distance.isEmpty() && time != null && !time.isEmpty()) {
                Map<String, String> record = new HashMap<>();
                record.put("date", date);
                record.put("distance", distance);
                record.put("time", time);
                results.add(record);
            }
        }

        if ("delete".equals(action)) {
            int index = Integer.parseInt(request.getParameter("index"));
            if (index >= 0 && index < results.size()) {
                results.remove(index);
            }
        }
    %>

    <h1>Результаты соревнований по гребле</h1>

    <div class="form-container">
        <h2>Добавить результат</h2>
        <form method="POST">
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
            <input type="text" id="time" name="time" placeholder="1:45.32" pattern="\d{1,2}:\d{2}\.\d{2}" required>

            <input type="hidden" name="action" value="add">
            <button type="submit">Добавить результат</button>
        </form>
    </div>

    <h2>Таблица результатов</h2>
    <% if (results.size() > 0) { %>
        <table>
            <tr>
                <th>Дата соревнования</th>
                <th>Дистанция</th>
                <th>Время</th>
                <th>Действие</th>
            </tr>
            <%
                for (int i = 0; i < results.size(); i++) {
                    Map<String, String> record = results.get(i);
            %>
            <tr>
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
    <% } else { %>
        <p>Нет сохраненных результатов.</p>
    <% } %>

    <p style="margin-top: 20px;">
        <a href="competition.jsp?clear=true" style="color: #e74c3c;">Очистить сессию</a>
    </p>

    <%
        String clear = request.getParameter("clear");
        if ("true".equals(clear)) {
            httpSession.removeAttribute("results");
            response.sendRedirect("competition.jsp");
        }
    %>
</body>
</html>
