package rowing.gwt.shared;

import java.io.Serializable;

/**
 * Класс для передачи результатов соревнований между клиентом и сервером
 */
public class CompetitionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String athleteName;
    private String date;
    private int distance;
    private String time;

    // Конструктор по умолчанию (обязателен для GWT)
    public CompetitionResult() {
    }

    // Конструктор с параметрами
    public CompetitionResult(String athleteName, String date, int distance, String time) {
        this.athleteName = athleteName;
        this.date = date;
        this.distance = distance;
        this.time = time;
    }

    // Геттеры и сеттеры
    public String getAthleteName() {
        return athleteName;
    }

    public void setAthleteName(String athleteName) {
        this.athleteName = athleteName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
