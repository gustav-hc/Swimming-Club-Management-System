package ui;

import controller.ClubController;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuTrainer {

    private final ClubController clubController;
    private final Scanner scanner;
    private final MenuHelper menuHelper;

    public MenuTrainer(ClubController clubController, Scanner scanner, MenuHelper menuHelper) {
        this.clubController = clubController;
        this.scanner = scanner;
        this.menuHelper = menuHelper;
    }

    public void showTrainerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    Tast:
                    1. Vis top 5 svømmere
                    2. Tilføj træningsresultater
                    3. Tilføj konkurrenceresultater
                    4. Tildel medlem "konkurrencesvømmer"
                    0. Tilbage til hovedmenu
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                System.out.println("Vil du se junior tider? (j/n): ");
                String juniorInput = scanner.nextLine().trim().toLowerCase();
                while (!juniorInput.equals("j") && !juniorInput.equals("n")) {
                    System.out.println("Indtast venligst 'j' for ja eller 'n' for nej: ");
                    juniorInput = scanner.nextLine().trim().toLowerCase();
                }
                boolean junior = juniorInput.equals("j");

                System.out.println("Vil du se konkurrenceresultater? (j/n): ");
                String competitionInput = scanner.nextLine().trim().toLowerCase();
                while (!competitionInput.equals("j") && !competitionInput.equals("n")) {
                    System.out.println("Indtast venligst 'j' for ja eller 'n' for nej: ");
                    competitionInput = scanner.nextLine().trim().toLowerCase();
                }
                boolean isCompetition = competitionInput.equals("j");

                Discipline discipline = menuHelper.showDisciplines();
                ArrayList<Member> top5 = clubController.getTop5Swimmers(junior, discipline, isCompetition);
                for (Member m : top5) {
                    ActiveMember active2 = (ActiveMember) m;
                    CompetitionSwimmer cs = active2.getCompetitionSwimmer();
                    int time = isCompetition ?
                            cs.getBestCompetitionResults(discipline) :
                            cs.getBestTrainingResults(discipline);
                    System.out.println(m.getName() + " - " + TrainingResult.formatTime(time));
                }

            } else if (choice == 2) {
                menuHelper.addTrainingResult();
            } else if (choice == 3) {
                menuHelper.addCompetitionResult();
            } else if (choice == 4) {
                menuHelper.makeCompetitionSwimmer();

            } else {
                running = false;
            }
        }
    }
}