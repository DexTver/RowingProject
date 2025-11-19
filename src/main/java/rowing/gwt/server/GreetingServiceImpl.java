package rowing.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import rowing.gwt.client.GreetingService;
import rowing.gwt.shared.CompetitionResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация удалённого сервиса на сервере
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

    private static List<CompetitionResult> database = new ArrayList<>();

    static {
        // Инициализация примерных данных
        database.add(new CompetitionResult("Иванов И.И.", "2025-11-15", 1000, "3:45.20"));
        database.add(new CompetitionResult("Петров П.П.", "2025-11-16", 500, "1:52.10"));
        database.add(new CompetitionResult("Сидоров С.С.", "2025-11-17", 2000, "7:15.45"));
    }

    @Override
    public List<CompetitionResult> getCompetitionResults() {
        return new ArrayList<>(database);
    }

    @Override
    public CompetitionResult addCompetitionResult(String athleteName, String date, int distance, String time) {
        CompetitionResult result = new CompetitionResult(athleteName, date, distance, time);
        database.add(result);
        return result;
    }

    @Override
    public void deleteCompetitionResult(int index) {
        if (index >= 0 && index < database.size()) {
            database.remove(index);
        }
    }
}
