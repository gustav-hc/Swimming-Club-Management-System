package ui;

import controller.ClubController;
import controller.MemberFeeController;
import model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuHelper {
    private final Scanner scanner;
    private final ClubController clubController;
    private final MemberFeeController memberFeeController;

    public MenuHelper(ClubController clubController, MemberFeeController memberFeeController, Scanner scanner) {
        this.clubController = clubController;
        this.memberFeeController = memberFeeController;
        this.scanner = scanner;
    }

    public Member findMemberById(int id) {
        for (Member m : clubController.getMembers()) {
            if (id == m.getMemberId()) {
                return m;
            }
        }
        return null;

    }

    public void showMemberList() {
        System.out.println("Liste over medlemmer");
        for (Member m : clubController.getMembers()) {
            if (m instanceof JuniorMember) {
                System.out.println("Junior: " + m);
            } else if (m instanceof SeniorMember) {
                System.out.println("Senior: " + m);
            } else {
                System.out.println("Passiv: " + m);
            }
        }
    }

    public void showCompetitionMemberList() {
        System.out.println("Liste over konkurrencesvømmere");
        for (Member m : clubController.getMembers()) {
            if (m instanceof ActiveMember active && active.getCompetitionSwimmer() != null) {
                System.out.println(m);
            }
        }
    }

    public Discipline showDisciplines() {
        System.out.println("""
                Tast:
                1. Fri
                2. Rygcrawl
                3. Bryst
                4. Butterfly
                5. Medley\s
               \s""");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                return Discipline.FREESTYLE;
            }
            case 2 -> {
                return Discipline.BACKSTROKE;
            }
            case 3 -> {
                return Discipline.BREASTSTROKE;
            }
            case 4 -> {
                return Discipline.BUTTERFLY;
            }
            case 5 -> {
                return Discipline.MEDLEY;
            }
            default -> {
                return null;
            }
        }
    }

    public CompetitionSwimmer checkCompetition() {
        showCompetitionMemberList();
        System.out.println("Indtast medlemsId på den svømmer du vil tildele et træningsresultat");
        int memberId = scanner.nextInt();
        scanner.nextLine();

        Member member = findMemberById(memberId);
        if (member instanceof ActiveMember active) {
            if (active.getCompetitionSwimmer() != null) {
                return active.getCompetitionSwimmer();
            }

        } else {
            System.out.println("Dette medlem er ikke en konkurrencesvømmer!");
            return null;
        }
        return null;
    }

    public void addTrainingResult() {
        CompetitionSwimmer cs = checkCompetition();
        if (cs != null) {
            Discipline discipline = showDisciplines();
            scanner.nextLine();
            System.out.println("Indtast tid (mm:ss.ms)");
            String time = scanner.nextLine();
            TrainingResult result = new TrainingResult(
                    TrainingResult.parseTime(time),
                    LocalDate.now(),
                    discipline
            );
            cs.addTrainingResult(result);
            System.out.println("Træningsresultat tilføjet!");
            clubController.saveData();
        }
    }

    public void addCompetitionResult() {
        CompetitionSwimmer cs = checkCompetition();
        if (cs != null) {
            Discipline discipline = showDisciplines();
            scanner.nextLine();
            System.out.println("Indtast stævnenavn");
            String eventName = scanner.nextLine();
            System.out.println("Indtast placering");
            int placement = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Indtast tid (mm:ss.ms)");
            String time = scanner.nextLine();
            CompetitionResult result = new CompetitionResult(
                    TrainingResult.parseTime(time),
                    LocalDate.now(),
                    discipline,
                    eventName,
                    placement
            );
            cs.addCompetitionResult(result);
            System.out.println("Konkurrenceresultat tilføjet!");
            clubController.saveData();
        }
    }

    public void makeCompetitionSwimmer() {
        showMemberList();
        System.out.println("Indtast medlemsid");
        int choice = scanner.nextInt();
        Member member = findMemberById(choice);
        if (member instanceof ActiveMember) {
            CompetitionSwimmer competitionSwimmer = new CompetitionSwimmer();
            ((ActiveMember) member).setCompetitionSwimmer(competitionSwimmer);
            System.out.println("Medlem gjort til konkurrence svømmer!");
            clubController.saveData();
        }
        else {
            System.out.println("Medlem passivt");
        }

    }

    public void updatePrices() {
        System.out.println("Nuværende junior pris: " + MemberFee.getJuniorPrice());
        System.out.println("Indtast ny junior pris (eller tryk ENTER for at beholde): ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            MemberFee.setJuniorPrice(Double.parseDouble(input));
        }

        System.out.println("Nuværende senior pris: " + MemberFee.getSeniorPrice());
        System.out.println("Indtast ny senior pris (tryk ENTER for at beholde):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            MemberFee.setSeniorPrice(Double.parseDouble(input));
        }

        System.out.println("Nuværende passiv pris: " + MemberFee.getPassivePrice());
        System.out.println("Indtast ny passiv pris (tryk ENTER for at beholde):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            MemberFee.setPassivePrice(Double.parseDouble(input));
        }

        System.out.println("Nuværende rabat multiplier: " +
                memberFeeController.getMemberFee().getSeniorDiscountMultiplier());
        System.out.println("Indtast ny rabat multiplier fx 0.75 for 25% rabat (tryk ENTER for at beholde):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            MemberFee.setSeniorDiscountMultiplier(Double.parseDouble(input));
        }

        memberFeeController.saveMemberFee();
        System.out.println("Priser gemt!");
    }
}