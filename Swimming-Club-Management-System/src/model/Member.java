package model;

import java.time.LocalDate;
import java.time.Year;

public abstract class   Member {
    private String name;
    private String phoneNumber;
    private int birthYear;
    private boolean active;
    private boolean hasPaid = false;        // Mike: Boolean der sættes til true, af kasseren når medlem har betalt.
    private LocalDate lastPaymentDate;// Mike: Sætter en dato, når kasseren har har sat en regning som betalt
    private int memberId;
    private static int nextId = 1;
    private MemberFee memberFee;

    public Member(String phoneNumber, String name, int birthYear, boolean active, MemberFee memberFee) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.birthYear = birthYear;
        this.active = active;
        this.memberFee = memberFee;
        this.memberId = nextId++;
        setHasPaid(false);
    }

    public String getName() {return name;}

    public String getPhoneNumber() {return phoneNumber;}

    public boolean isActive() {return active;}

    public int getBirthYear() {
        return birthYear;
    }

    public int getAgeNextYear() {
        return Year.now().getValue() + 1 - birthYear;
    }

    public int getAge() {
        return Year.now().getValue()-birthYear;}

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
        if (hasPaid) {
            this.lastPaymentDate = LocalDate.now();
        }
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public abstract double calculateMemberFee();

    @Override
    public String toString() {
        return  "nr. " + memberId + " - " +  name  + " - tlf nr. " + phoneNumber + " - " + getAge();
    }

    public String toStringKasserer() {
        return "nr: " + memberId + " - Navn: " + name + " - tlf nr: " + phoneNumber + " - betalt: " + hasPaid;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public static void setNextId(int nextId) {
        Member.nextId = nextId;
    }

    public MemberFee getMemberFee() {
        return memberFee;
    }
}
