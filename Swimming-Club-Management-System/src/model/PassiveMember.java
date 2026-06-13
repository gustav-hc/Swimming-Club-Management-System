package model;

public class PassiveMember extends Member {

    public PassiveMember(String phoneNumber, String name, int birthYear, MemberFee memberFee) {
        super(phoneNumber, name, birthYear, false, memberFee);
    }

    public double calculateMemberFee() {
        return getMemberFee().getPassivePrice();
    }
}