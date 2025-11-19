package rowing.gwt.client;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import rowing.gwt.shared.CompetitionResult;

/**
 * Интерфейс удалённого сервиса для получения результатов соревнований
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

    List<CompetitionResult> getCompetitionResults();

    CompetitionResult addCompetitionResult(String athleteName, String date, int distance, String time);

    void deleteCompetitionResult(int index);
}
