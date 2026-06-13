package controller;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClubControllerTest {

    @Test
    public void getTop5Swimmers() {
        ClubController controller = new ClubController();

        controller.addMember("12345678", "Anders", 15, true, true);
        controller.addMember("12345679", "Bjarne", 14, true, true);
        controller.addMember("12345680", "Christian", 16, true, true);

// Hent dem ud og tilføj CompetitionSwimmer med resultater
        JuniorMember anders = (JuniorMember) controller.getMembers().get(0);
        CompetitionSwimmer cs1 = new CompetitionSwimmer();
        cs1.addTrainingResult(new TrainingResult(65000, LocalDate.now(), Discipline.FREESTYLE));
        anders.setCompetitionSwimmer(cs1);
        ArrayList<Member> result = controller.getTop5Swimmers(true, Discipline.FREESTYLE, false);

        assertEquals(anders, result.get(0));
        assertInstanceOf(JuniorMember.class, result.get(0));
    }

}