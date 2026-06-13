package model;

public class JuniorMember extends ActiveMember {

    public JuniorMember(String phoneNumber, String name, int age, boolean isCompetition, MemberFee memberFee) {
        super(phoneNumber, name, age, isCompetition, memberFee);
    }

    public double calculateMemberFee() {
        return getMemberFee().getJuniorPrice();
    }
}
