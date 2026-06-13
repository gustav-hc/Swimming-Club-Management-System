package controller;

import model.JuniorMember;
import model.Member;
import model.MemberFee;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberFeeController {
    private final ClubController clubController;
    private final AgeController ageController;
    private LocalDate lastReset;
    private final MemberFee memberFee;

    public MemberFeeController(ClubController clubController) {
        this.memberFee = new MemberFee();
        this.clubController = clubController;
        this.ageController = new AgeController(clubController);
        clubController.setMemberFee(memberFee);
        loadMemberFee();
        loadLastReset();
        checkYearlyReset();
    }

    public void registerPayment(Member member) {
        member.setHasPaid(true);
    }

    public List<Member> getMembersInArrears() {
        List<Member> arrears = new ArrayList<>();
        for (Member m : clubController.getMembers()) {
            if (!m.isHasPaid()) {
                arrears.add(m);
            }
        }
        return arrears;
    }

    private void loadLastReset() {
        try (Scanner scanner = new Scanner(new File("lastReset.txt"))) {
            if (scanner.hasNextLine()) {
                lastReset = LocalDate.parse(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            lastReset = null;
        }
    }

    private void checkYearlyReset() {
        LocalDate today = LocalDate.now();

        if (lastReset == null || lastReset.getYear() < today.getYear()) {
            ageController.checkAgeJuniorToSenior();
            resetPayments();
            lastReset = today;
            saveLastReset();
        }
    }

    private void resetPayments() {
        for (Member m : clubController.getMembers()) {
            if (!m.isHasPaid()) {
                System.out.println("ADVARSEL! " + m.getMemberId() + " - " + m.getName() + " betalte ikke sidste år!");
            }
            m.setHasPaid(false);
        }
    }

    private void saveLastReset() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("lastReset.txt"))) {
            writer.println(lastReset);
        } catch (IOException e) {
            System.out.println("Fejl ved lagring af dato: " + e.getMessage());
        }
    }

    public double getTotalExpectedMemberFee() {
        double total = 0;
        for (Member m : clubController.getMembers()) {
            if (m instanceof JuniorMember && m.getAgeNextYear() >= 18) {
                total += MemberFee.getSeniorPrice();
            } else {
                total += m.calculateMemberFee();
            }
        }
        return total;
    }

    public MemberFee getMemberFee() {
        return memberFee;
    }

    public void saveMemberFee() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("memberFee.txt"))) {
            writer.println(MemberFee.getJuniorPrice());
            writer.println(MemberFee.getSeniorPrice());
            writer.println(MemberFee.getPassivePrice());
            writer.println(getMemberFee().getSeniorDiscountMultiplier());
        } catch (IOException e) {
            System.out.println("Fejl ved lagring af priser: " + e.getMessage());
        }
    }

    private void loadMemberFee() {
        try (Scanner scanner = new Scanner(new File("memberFee.txt"))) {
            MemberFee.setJuniorPrice(Double.parseDouble(scanner.nextLine()));
            MemberFee.setSeniorPrice(Double.parseDouble(scanner.nextLine()));
            MemberFee.setPassivePrice(Double.parseDouble(scanner.nextLine()));
            MemberFee.setSeniorDiscountMultiplier(Double.parseDouble(scanner.nextLine()));
        } catch (FileNotFoundException e) {
            System.out.println("Fejl ved indlæsning: " + e.getMessage());
        }
    }
}