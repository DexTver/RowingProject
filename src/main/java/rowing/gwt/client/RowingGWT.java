package rowing.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import rowing.gwt.shared.CompetitionResult;

import java.util.List;

/**
 * Entry point для GWT-приложения "Соревнования по гребле"
 */
public class RowingGWT implements EntryPoint {

    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
    private CellTable<CompetitionResult> table;
    private Label statusLabel;

    public void onModuleLoad() {
        // Создание главной панели
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");

        // Заголовок
        Label title = new Label("🏊 Результаты соревнований по гребле");
        title.setStyleName("gwt-Label-Title");
        mainPanel.add(title);

        // Статус сообщения
        statusLabel = new Label();
        statusLabel.setStyleName("gwt-Label-Status");
        mainPanel.add(statusLabel);

        // Форма добавления результата
        HorizontalPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);

        // Таблица результатов
        table = createTable();
        mainPanel.add(table);

        // Загрузка данных с сервера
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
                    statusLabel.setText("❌ Заполните все поля!");
                    return;
                }

                try {
                    int distance = Integer.parseInt(distanceStr);
                    addResult(name, date, distance, time);
                    nameBox.setValue("");
                    dateBox.setValue("");
                    timeBox.setValue("");
                } catch (NumberFormatException e) {
                    statusLabel.setText("❌ Ошибка в формате дистанции!");
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

        // Столбец "Имя спортсмена"
        TextColumn<CompetitionResult> nameColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getAthleteName();
            }
        };
        cellTable.addColumn(nameColumn, "Спортсмен");

        // Столбец "Дата"
        TextColumn<CompetitionResult> dateColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getDate();
            }
        };
        cellTable.addColumn(dateColumn, "Дата");

        // Столбец "Дистанция"
        TextColumn<CompetitionResult> distanceColumn = new TextColumn<CompetitionResult>() {
            @Override
            public String getValue(CompetitionResult object) {
                return object.getDistance() + " м";
            }
        };
        cellTable.addColumn(distanceColumn, "Дистанция");

        // Столбец "Время"
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
        greetingService.getCompetitionResults(new AsyncCallback<List<CompetitionResult>>() {
            public void onFailure(Throwable caught) {
                statusLabel.setText("❌ Ошибка загрузки данных: " + caught.getMessage());
            }

            public void onSuccess(List<CompetitionResult> result) {
                table.setRowCount(result.size(), true);
                table.setRowData(0, result);
                statusLabel.setText("✅ Загружено результатов: " + result.size());
            }
        });
    }

    private void addResult(String name, String date, int distance, String time) {
        greetingService.addCompetitionResult(name, date, distance, time,
            new AsyncCallback<CompetitionResult>() {
                public void onFailure(Throwable caught) {
                    statusLabel.setText("❌ Ошибка добавления: " + caught.getMessage());
                }

                public void onSuccess(CompetitionResult result) {
                    statusLabel.setText("✅ Результат добавлен успешно!");
                    loadResults();
                }
            });
    }
}
