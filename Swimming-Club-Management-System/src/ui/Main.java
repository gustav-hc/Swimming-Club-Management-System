package ui;

import controller.ClubController;
import controller.MemberFeeController;

public class Main {
    public static void main(String[] args) {
        ClubController clubController = new ClubController();
        MemberFeeController memberFeeController = new MemberFeeController(clubController);
        Menu menu = new Menu(clubController, memberFeeController);
        menu.start();
    }
}
