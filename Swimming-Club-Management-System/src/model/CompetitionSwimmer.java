package model;


import java.util.ArrayList;

public class CompetitionSwimmer {
    private final ArrayList<TrainingResult> trainingResults;
    private final ArrayList<CompetitionResult> competitionResults;
    private final ArrayList<Discipline> disciplines;

    public CompetitionSwimmer() {
        trainingResults = new ArrayList<>();
        competitionResults = new ArrayList<>();
        disciplines = new ArrayList<>();

    }
    public void addTrainingResult(TrainingResult result) {
        trainingResults.add(result);
    }

    public void addCompetitionResult(CompetitionResult result) {
        competitionResults.add(result);
    }

    public ArrayList<TrainingResult> getTrainingResults() {return trainingResults;}

    public ArrayList<Discipline> getDisciplines() {return disciplines;}

    public ArrayList<CompetitionResult> getCompetitionResults() {return competitionResults;}

    public int getBestTrainingResults(Discipline discipline) {

        int time = Integer.MAX_VALUE;
        for (TrainingResult i : getTrainingResults()) {
            if (i.getDiscipline() == discipline) {
                if (i.getTimeResult() < time) {
                    time = i.getTimeResult();
                }
            }
        }
        return time;
    }

    public int getBestCompetitionResults(Discipline discipline) {

        int time = Integer.MAX_VALUE;
        for (CompetitionResult i : getCompetitionResults()) {
            if (i.getDiscipline() == discipline) {
                if (i.getTimeResult() < time) {
                    time = i.getTimeResult();
                }
            }
        }
        return time;
    }


}
