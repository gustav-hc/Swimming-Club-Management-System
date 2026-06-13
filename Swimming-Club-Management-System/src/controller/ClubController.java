package controller;

import data.FileHandler;
import model.*;
import model.Member;

import java.time.Year;
import java.util.ArrayList;

public class ClubController {
    private final ArrayList<Member> members;
    private MemberFee memberFee;
    private final FileHandler fileHandler = new FileHandler(null);

    public ClubController() {
        this.memberFee = new MemberFee();
        this.members = fileHandler.loadMembers();
    }

    public void setMemberFee(MemberFee memberFee) {
        this.memberFee = memberFee;
    }

    public void addMember(String phoneNumber, String name, int age,
                          boolean isCompetition, boolean isActive) {
        Member newMember;
        int calculatedAge = Year.now().getValue() - age;

        if (!isActive) {
            newMember = new PassiveMember(phoneNumber, name, age, memberFee);
        } else if (calculatedAge <= 18) {
            newMember = new JuniorMember(phoneNumber, name, age, isCompetition, memberFee);
        } else {
            newMember = new SeniorMember(phoneNumber, name, age, isCompetition, memberFee);
        }

        members.add(newMember);
        fileHandler.saveMembers(members);
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void saveData() {
        fileHandler.saveMembers(members);
    }


    public ArrayList<Member> getTop5Swimmers(boolean isJunior, Discipline discipline, boolean isCompetition) {
        ArrayList<Member> member = new ArrayList<>();
        for (Member s : members) {
            if (isJunior && s instanceof JuniorMember active) {
                if (active.getCompetitionSwimmer() != null) {
                    member.add(s);
                }
            } else if (!isJunior && s instanceof SeniorMember active) {
                if (active.getCompetitionSwimmer() != null) {
                    member.add(s);
                }
            }

        }
        member.sort(new SwimmerComparator(discipline, isCompetition));
        return new ArrayList<>(member.subList(0, Math.min(5, member.size())));
    }
}
