package model;

public class SeniorMember extends ActiveMember {

    public SeniorMember(String phoneNumber, String name, int age, boolean isCompetition, MemberFee memberFee) {
        super(phoneNumber, name, age, isCompetition, memberFee);
    }

    public double calculateMemberFee() {
        if (getAge() >= 60) {
            return getMemberFee().getSeniorDiscountPrice() ;
        }
        return getMemberFee().getSeniorPrice();
    }

}
