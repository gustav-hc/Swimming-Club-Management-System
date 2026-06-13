package ui;

import controller.MemberFeeController;
import model.Member;

import java.util.Scanner;

public class MenuTreasure {
        private final Scanner scanner;
        private final MenuHelper menuHelper;
        private final MemberFeeController memberFeeController;
    public MenuTreasure(Scanner scanner, MenuHelper menuHelper, MemberFeeController memberFeeController) {
        this.scanner = scanner;
        this.menuHelper = menuHelper;
        this.memberFeeController = memberFeeController;
    }

    public void showTreasureMenu() {
        boolean running = true;
        while (running) {
            System.out.println("""
                    Tast:
                    1. Registrer betaling
                    2. Se medlemmer i restance
                    3. Se forventet medlemskab gebyr
                    0. Tilbage til hovedmenu
                    """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                menuHelper.showMemberList();
                System.out.println("Indtast medlemsnummeret på det medlem du vil registrere betalingen på ");
                int number = scanner.nextInt();
                scanner.nextLine();
                Member member = menuHelper.findMemberById(number);
                memberFeeController.registerPayment(member);
                System.out.println("Betalingen er nu registreret");

            } else if (choice == 2) {
                System.out.println(memberFeeController.getMembersInArrears());

            } else if (choice == 3) {
                System.out.println(memberFeeController.getTotalExpectedMemberFee());

            } else running = false;
        }
    }
}