package model;

import java.time.LocalDate;

public class CompetitionResult extends TrainingResult {
    private String competitionName;
    private int placement;

    public CompetitionResult(int timeResult, LocalDate date, Discipline discipline, String competitionName, int placement) {
        super(timeResult, date, discipline);
        this.competitionName = competitionName;
        this.placement = placement;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public int getPlacement() {
        return placement;
    }

}
