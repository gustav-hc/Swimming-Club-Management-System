package ui;

import controller.ClubController;
import controller.MemberFeeController;

import java.util.Scanner;

public class Menu {
    private final MenuTreasure menuTreasure;
    private final Scanner scanner;
    private final MenuChairman menuChairman;
    private final MenuTrainer menuTrainer;

    public Menu(ClubController clubController, MemberFeeController memberFeeController) {
        this.scanner = new Scanner(System.in);

        MenuHelper menuHelper = new MenuHelper(clubController, memberFeeController, scanner);

        this.menuChairman = new MenuChairman(clubController, scanner, menuHelper);
        this.menuTrainer = new MenuTrainer(clubController, scanner, menuHelper);
        this.menuTreasure = new MenuTreasure(scanner, menuHelper, memberFeeController);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    Tast:
                    1. Formand
                    2. Kassere
                    3. Træner
                    4. Afslut
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                menuChairman.showChairmanMenu();
            } else if (choice == 2) {
                menuTreasure.showTreasureMenu();
            } else if (choice == 3) {
                menuTrainer.showTrainerMenu();
            } else running = false;
        }
    }



}



