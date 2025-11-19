package rowing.gwt.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import rowing.gwt.shared.CompetitionResult;

public interface GreetingServiceAsync {

    void getCompetitionResults(AsyncCallback<List<CompetitionResult>> callback);

    void addCompetitionResult(String athleteName, String date, int distance, String time,
                              AsyncCallback<CompetitionResult> callback);

    void deleteCompetitionResult(int index, AsyncCallback<Void> callback);
}
