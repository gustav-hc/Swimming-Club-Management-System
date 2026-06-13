package ui;

import controller.ClubController;

import java.util.Scanner;

public class MenuChairman {
    private final MenuHelper menuHelper;
    private final ClubController clubController;
    private final Scanner scanner;


    public MenuChairman(ClubController clubController, Scanner scanner, MenuHelper menuHelper) {
        this.clubController = clubController;
        this.scanner = scanner;
        this.menuHelper = menuHelper;
    }

    public void showChairmanMenu() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    Tast:\s
                    1. Opret medlem
                    2. Se medlemmer
                    3. Ændre medlemspriser
                    0. Tilbage til hovedmenu\s""");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                System.out.println("Indtast telefon nummer: ");
                String phoneNumber = scanner.nextLine();
                System.out.println("Indtast fulde navn: ");
                String name = scanner.nextLine();
                System.out.println("Indtast fødselsår: ");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Skal personen være en konkurrencesvømmer? (j/n): ");
                String competitorInput = scanner.nextLine().trim().toLowerCase();
                while (!competitorInput.equals("j") && !competitorInput.equals("n")) {
                    System.out.println("Indtast venligst 'j' for ja eller 'n' for nej: ");
                    competitorInput = scanner.nextLine().trim().toLowerCase();
                }
                boolean competitor = competitorInput.equals("j");

                System.out.println("Skal medlemmet registreres som aktivt? (j/n): ");
                String activeInput = scanner.nextLine().trim().toLowerCase();
                while (!activeInput.equals("j") && !activeInput.equals("n")) {
                    System.out.println("Indtast venligst 'j' for ja eller 'n' for nej: ");
                    activeInput = scanner.nextLine().trim().toLowerCase();
                }
                boolean active = activeInput.equals("j");
                System.out.println("Medlem oprettet!");
                clubController.addMember(phoneNumber, name, age, competitor, active);

            } else if (choice == 2) {
                menuHelper.showMemberList();

            } else if (choice == 3) {
                menuHelper.updatePrices();

            } else running = false;
        }
    }
}