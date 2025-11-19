package rowing.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import rowing.gwt.shared.CompetitionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Entry point для GWT-приложения "Соревнования по гребле"
 * Использует прямые HTTP запросы вместо GWT RPC для совместимости с Tomcat 10
 */
public class RowingGWT implements EntryPoint {

    private CellTable<CompetitionResult> table;
    private Label statusLabel;
    private List<CompetitionResult> currentData = new ArrayList<>();

    public void onModuleLoad() {
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");

        Label title = new Label("Результаты соревнований по гребле");
        title.setStyleName("gwt-Label-Title");
        mainPanel.add(title);

        statusLabel = new Label();
        statusLabel.setStyleName("gwt-Label-Status");
        mainPanel.add(statusLabel);

        HorizontalPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);

        table = createTable();
        mainPanel.add(table);

        loadResults();

        RootPanel.get("mainContainer").add(mainPanel);
    }

    private HorizontalPanel createFormPanel() {
        HorizontalPanel panel = new HorizontalPanel();
        panel.setSpacing(10);

        final TextBox nameBox = new TextBox();
        nameBox.getElement().setAttribute("placeholder", "Имя спортсмена");

        final TextBox dateBox = new TextBox();
        dateBox.getElement().setAttribute("placeholder", "Дата (YYYY-MM-DD)");

        final ListBox distanceBox = new ListBox();
        distanceBox.addItem("500 м", "500");
        distanceBox.addItem("1000 м", "1000");
        distanceBox.addItem("2000 м", "2000");
        distanceBox.addItem("5000 м", "5000");

        final TextBox timeBox = new TextBox();
        timeBox.getElement().setAttribute("placeholder", "Время (MM:SS.MS)");

        Button addButton = new Button("Добавить результат");
        addButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String name = nameBox.getValue();
                String date = dateBox.getValue();
                String distanceStr = distanceBox.getValue(distanceBox.getSelectedIndex());
                String time = timeBox.getValue();

                if (name.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    statusLabel.setText("Заполните все поля!");
                    return;
                }

                try {
                    int distance = Integer.parseInt(distanceStr);
                    addResult(name, date, distance, time);
                    nameBox.setValue("");
                    dateBox.setValue("");
                    timeBox.setValue("");
                } catch (Exception e) {
                    statusLabel.setText("Ошибка: " + e.getMessage());
                }
            }
        });

        panel.add(new Label("Имя:"));
        panel.add(nameBox);
        panel.add(new Label("Дата:"));
        panel.add(dateBox);
        panel.add(new Label("Дистанция:"));
        panel.add(distanceBox);
        panel.add(new Label("Время:"));
        panel.add(timeBox);
        panel.add(addButton);

        return panel;
    }

    private CellTable<CompetitionResult> createTable() {
        CellTable<CompetitionResult> cellTable = new CellTable<>();

        TextColumn<CompetitionResult> nameColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getAthleteName();
            }
        };
        cellTable.addColumn(nameColumn, "Спортсмен");

        TextColumn<CompetitionResult> dateColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getDate();
            }
        };
        cellTable.addColumn(dateColumn, "Дата");

        TextColumn<CompetitionResult> distanceColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getDistance() + " м";
            }
        };
        cellTable.addColumn(distanceColumn, "Дистанция");

        TextColumn<CompetitionResult> timeColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getTime();
            }
        };
        cellTable.addColumn(timeColumn, "Время");

        return cellTable;
    }

    private void loadResults() {
        String url = GWT.getModuleBaseURL() + "greet";

        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);

        try {
            builder.sendRequest(null, new RequestCallback() {
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == 200) {
                        parseAndDisplayResults(response.getText());
                    } else {
                        statusLabel.setText("Ошибка: " + response.getStatusCode() + " - " + response.getStatusText());
                    }
                }

                public void onError(Request request, Throwable exception) {
                    statusLabel.setText("Ошибка загрузки: " + exception.getMessage());
                }
            });
        } catch (RequestException e) {
            statusLabel.setText("Ошибка запроса: " + e.getMessage());
        }
    }

    private void parseAndDisplayResults(String json) {
        try {
            currentData.clear();
            JSONValue value = JSONParser.parseStrict(json);
            JSONArray array = value.isArray();

            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.get(i).isObject();
                    if (obj != null) {
                        String name = obj.get("athleteName").isString().stringValue();
                        String date = obj.get("date").isString().stringValue();
                        int distance = (int) obj.get("distance").isNumber().doubleValue();
                        String time = obj.get("time").isString().stringValue();

                        currentData.add(new CompetitionResult(name, date, distance, time));
                    }
                }

                table.setRowCount(currentData.size(), true);
                table.setRowData(0, currentData);
                statusLabel.setText("Загружено результатов: " + currentData.size());
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка парсинга: " + e.getMessage());
        }
    }

    private void addResult(String name, String date, int distance, String time) {
        currentData.add(new CompetitionResult(name, date, distance, time));
        table.setRowCount(currentData.size(), true);
        table.setRowData(0, currentData);
        statusLabel.setText("Результат добавлен! Всего: " + currentData.size());
    }
}
