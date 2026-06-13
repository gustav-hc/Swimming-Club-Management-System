package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class TrainingResultTest {
    @Test
    public void ParsedTimeTest() {
        TrainingResult trainingResult = new TrainingResult(80000, LocalDate.ofYearDay(2026, 30), Discipline.MEDLEY);
        int result = trainingResult.parseTime("01:45.457");
        assertEquals(105457, result);


    }

    @Test
    public void formatTimeTest() {
        TrainingResult trainingResult = new TrainingResult(80000, LocalDate.ofYearDay(2026, 30), Discipline.MEDLEY);
        String result = trainingResult.formatTime(105457);
        assertEquals("01:45.457", result);
    }
}

