package rowing.gwt.server;

import rowing.gwt.client.GreetingService;
import rowing.gwt.shared.CompetitionResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GreetingServiceImpl extends HttpServlet implements GreetingService {

    private static final long serialVersionUID = 1L;

    private static List<CompetitionResult> database = new ArrayList<>();

    static {
        database.add(new CompetitionResult("Иванов И.И.", "2025-11-15", 1000, "3:45.20"));
        database.add(new CompetitionResult("Петров П.П.", "2025-11-16", 500, "1:52.10"));
        database.add(new CompetitionResult("Сидоров С.С.", "2025-11-17", 2000, "7:15.45"));
        System.out.println("✅ GreetingServiceImpl инициализирован. Загружено " + database.size() + " результатов");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("✅ doPost() вызван");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        System.out.println("PathInfo: " + pathInfo);

        PrintWriter out = response.getWriter();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < database.size(); i++) {
            CompetitionResult result = database.get(i);
            json.append("{");
            json.append("\"athleteName\":\"").append(result.getAthleteName()).append("\",");
            json.append("\"date\":\"").append(result.getDate()).append("\",");
            json.append("\"distance\":").append(result.getDistance()).append(",");
            json.append("\"time\":\"").append(result.getTime()).append("\"");
            json.append("}");
            if (i < database.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        out.print(json.toString());
        out.flush();

        System.out.println("✅ Отправлен JSON: " + json.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("✅ doGet() вызван");
        doPost(request, response);
    }

    @Override
    public List<CompetitionResult> getCompetitionResults() {
        System.out.println("✅ getCompetitionResults() вызван. Возвращаем: " + database.size() + " результатов");
        return new ArrayList<>(database);
    }

    @Override
    public CompetitionResult addCompetitionResult(String athleteName, String date, int distance, String time) {
        System.out.println("✅ addCompetitionResult() вызван: " + athleteName + ", " + date + ", " + distance + "м, " + time);
        CompetitionResult result = new CompetitionResult(athleteName, date, distance, time);
        database.add(result);
        System.out.println("Добавлен. Всего результатов: " + database.size());
        return result;
    }

    @Override
    public void deleteCompetitionResult(int index) {
        System.out.println("✅ deleteCompetitionResult() вызван: индекс " + index);
        if (index >= 0 && index < database.size()) {
            database.remove(index);
            System.out.println("Удалён. Осталось: " + database.size());
        }
    }
}
