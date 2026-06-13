package model;

import java.time.LocalDate;

public class  TrainingResult {
    private int timeResult;
    private LocalDate date;
    private Discipline discipline;

    public TrainingResult(int timeResult, LocalDate date, Discipline discipline) {
        this.timeResult = timeResult;
        this.date = date;
        this.discipline = discipline;
    }

    public static int parseTime(String time) {
        String[] splitter = time.split(":");
        String[] splitter1 = splitter[1].split("\\.");

        int h = 0;
        h += Integer.parseInt(splitter[0]) * 60000;
        h += Integer.parseInt(splitter1[0]) * 1000;
        h += Integer.parseInt(splitter1[1]);
        return h;
    }

    public static String formatTime(int timeResult) {

        String fulltext;
        int minutes =0;
        int seconds =0;
        int milliSeconds = 0;

        minutes += (timeResult / 60000);
        seconds += (timeResult % 60000) / 1000;
        milliSeconds += timeResult % 1000;
        return  String.format("%02d:%02d.%03d",minutes, seconds, milliSeconds);
    }

    public int getTimeResult() {return timeResult;}

    public LocalDate getDate() {return date;}

    public Discipline getDiscipline() {return discipline;}
}
