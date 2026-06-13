package model;

import controller.ClubController;
import controller.MemberFeeController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberFeeTest {
    private MemberFee memberFee;
    private int juniorBirthYear;
    private int seniorBirthYear;
    private int seniorPlusBirthYear;
    private ClubController clubController;
    private MemberFeeController memberFeeController;

    @BeforeEach
    public void setUp() {
        memberFee = new MemberFee();
        MemberFee.setJuniorPrice(1000);
        MemberFee.setSeniorPrice(1600);
        MemberFee.setPassivePrice(500);
        MemberFee.setSeniorDiscountMultiplier(0.75);

        int currentYear = Year.now().getValue();
        juniorBirthYear = currentYear - 15;
        seniorBirthYear = currentYear - 30;
        seniorPlusBirthYear = currentYear - 65;

        clubController = new ClubController();
        memberFeeController = new MemberFeeController(clubController);
    }

    @Test
    public void testJuniorMemberFee() {
        JuniorMember junior = new JuniorMember("22844955", "Junior Personb", juniorBirthYear, false, memberFee);
        assertEquals(1000, junior.calculateMemberFee());
    }

    @Test
    public void testSeniorMemberFee() {
        SeniorMember senior = new SeniorMember("12345678", "Senior Person", seniorBirthYear, false, memberFee);
        assertEquals(1600, senior.calculateMemberFee());
    }

    @Test
    public void testPassiveMemberFee() {
        PassiveMember passive = new PassiveMember("12345678", "Passiv Person", seniorBirthYear, memberFee);
        assertEquals(500, passive.calculateMemberFee());
    }

    @Test
    public void testSeniorPlusDiscount() {
        SeniorMember seniorPlus = new SeniorMember("12345678", "Gammel Person", seniorPlusBirthYear, false, memberFee);
        assertEquals(1200, seniorPlus.calculateMemberFee()); // 1600 * 0.75 = 1200
    }

    @Test
    public void testGetTotalExpectedMemberFee() {
        clubController.getMembers().clear();
        // En junior der IKKE fylder 18 næste år -> junior pris
        int youngJuniorBirthYear = Year.now().getValue() - 15;
        JuniorMember junior = new JuniorMember("11111111", "Ung Junior", youngJuniorBirthYear, false, memberFee);

        // En junior der fylder 18 næste år -> skal tælle som senior
        int turningAdultBirthYear = Year.now().getValue() - 17;
        JuniorMember almostSenior = new JuniorMember("22222222", "Næsten Senior", turningAdultBirthYear, false, memberFee);

        // En senior -> senior pris
        int seniorBirthYear = Year.now().getValue() - 30;
        SeniorMember senior = new SeniorMember("33333333", "Senior Person", seniorBirthYear, false, memberFee);

        // En senior over 60 -> rabat pris
        int over60BirthYear = Year.now().getValue() - 65;
        SeniorMember seniorOver60 = new SeniorMember("44444444", "Gammel Senior", over60BirthYear, false, memberFee);

        // En passiv -> passiv pris
        PassiveMember passive = new PassiveMember("55555555", "Passiv Person", seniorBirthYear, memberFee);

        List<Member> members = new ArrayList<>();
        members.add(junior);
        members.add(almostSenior);
        members.add(senior);
        members.add(seniorOver60);
        members.add(passive);

        double expected = 1000 + 1600 + 1600 + 1200 + 500;

        clubController.getMembers().addAll(members);

        assertEquals(expected, memberFeeController.getTotalExpectedMemberFee());
    }
}