package controller;

import model.JuniorMember;
import model.Member;
import model.SeniorMember;

import java.util.ArrayList;

public class AgeController {
    private final ClubController clubController;

    protected AgeController(ClubController clubController) {
        this.clubController = clubController;
    }

    public void checkAgeJuniorToSenior() {
        ArrayList<Member> toRemove = new ArrayList<>();
        ArrayList<Member> toAdd = new ArrayList<>();

        for (
                Member m : clubController.getMembers()) {
            if (m instanceof JuniorMember junior && m.getAge() >= 18) {

                SeniorMember senior = new SeniorMember(
                        junior.getPhoneNumber(),
                        junior.getName(),
                        junior.getBirthYear(),
                        junior.getIsCompetition(),
                        junior.getMemberFee()
                );
                senior.setMemberId(junior.getMemberId());

                toRemove.add(junior);
                toAdd.add(senior);

                System.out.println(junior.getName() + " er opgraderet til senior!");
            }
        }

        clubController.getMembers().removeAll(toRemove);
        clubController.getMembers().addAll(toAdd);
    }
}


