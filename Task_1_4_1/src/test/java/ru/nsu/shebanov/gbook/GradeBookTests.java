package ru.nsu.shebanov.gbook;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradeBookTests {

    @Test
    void semesterTests(){
        Map<String, Integer> controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 2);
        controlForms.put("коллоквиум", 2);
        controlForms.put("экзамен", 2);
        Semester sem = new Semester(controlForms);
        System.out.println(sem.marksNumber);

        sem.setMark("задание", 2);
        sem.setMark("коллоквиум", 2);
        sem.setMark("экзамен", 5);

        assertTrue(sem.noExamC());
    }

    @Test
    void failedSemesterTest() {
        Map<String, Integer> controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 2);
        controlForms.put("коллоквиум", 2);
        controlForms.put("экзамен", 1);
        controlForms.put("зачёт", 1);
        controlForms.put("дифференцированный зачёт", 1);
        Semester sem = new Semester(controlForms);
        System.out.println(sem.marksNumber);

        sem.setMark("задание", 2);
        sem.setMark("коллоквиум", 2);
        sem.setMark("экзамен", 2);
        sem.setMark("зачёт", 5);
        sem.setMark("дифференцированный зачёт", 5);


        assertFalse(sem.noExamC());
    }

    @Test
    void incorrectFormOfControl() {
        Map<String, Integer> controlForms = new HashMap<>();
        controlForms.put("избиение палками", 1488);

        try {
            Semester sem = new Semester(controlForms);
            fail();
        } catch(IllegalArgumentException ignored){

        }
    }


    @Test
    void basicSubjectTest() {
        Map<String, Integer> controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        Semester sem1 = new Semester(controlForms);

        controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        Semester sem2 = new Semester(controlForms);

        controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        controlForms.put("экзамен", 1);
        controlForms.put("зачёт", 1);
        controlForms.put("дифференцированный зачёт", 1);
        Semester sem3 = new Semester(controlForms);


        Subject sub = new Subject("Кораблестроение", Arrays.asList(sem1, sem2, sem3), Arrays.asList(3,4,5));
        sub.setMark("контрольная", 3, 2);
        sub.setMark("контрольная", 3, 2);
        sub.setMark("коллоквиум", 3, 2);
        sub.setMark("контрольная", 3, 2);
        sub.setMark("задание", 3, 2);

        sub.setMark("контрольная", 4, 2);
        sub.setMark("контрольная", 4, 2);
        sub.setMark("коллоквиум", 4, 2);
        sub.setMark("контрольная", 4, 2);
        sub.setMark("задание", 4, 2);

        sub.setMark("контрольная", 5, 2);
        sub.setMark("контрольная", 5, 2);
        sub.setMark("коллоквиум", 5, 2);
        sub.setMark("контрольная", 5, 2);
        sub.setMark("задание", 5, 2);
        sub.setMark("экзамен", 5, 5);


        List<Integer> expected = new ArrayList<>(Arrays.asList(16,35));
        assertEquals(expected, sub.marksExtract());
        assertFalse(sub.lastSemesterNoC());
        assertTrue(sub.allTimeNoCForExams());

    }

    @Test
    void lastSemesterSubject() {
        Map<String, Integer> controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        Semester sem1 = new Semester(controlForms);

        controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        Semester sem2 = new Semester(controlForms);

        controlForms = new HashMap<>();
        controlForms.put("задание", 5);
        controlForms.put("контрольная", 3);
        controlForms.put("коллоквиум", 2);
        controlForms.put("экзамен", 1);
        controlForms.put("зачёт", 1);
        controlForms.put("дифференцированный зачёт", 1);
        Semester sem3 = new Semester(controlForms);


        Subject sub = new Subject("Кораблестроение", Arrays.asList(sem1, sem2, sem3), Arrays.asList(3,4,5));
        sub.setMark("контрольная", 3, 2);
        sub.setMark("контрольная", 3, 2);
        sub.setMark("коллоквиум", 3, 2);
        sub.setMark("контрольная", 3, 2);
        sub.setMark("задание", 3, 2);

        sub.setMark("контрольная", 4, 2);
        sub.setMark("контрольная", 4, 2);
        sub.setMark("коллоквиум", 4, 2);
        sub.setMark("контрольная", 4, 2);
        sub.setMark("задание", 4, 2);

        sub.setMark("контрольная", 5, 5);
        sub.setMark("контрольная", 5, 5);
        sub.setMark("коллоквиум", 5, 5);
        sub.setMark("контрольная", 5, 5);
        sub.setMark("задание", 5, 5);
        sub.setMark("экзамен", 5, 5);


        List<Integer> expected = new ArrayList<>(Arrays.asList(16,50));
        assertEquals(expected, sub.marksExtract());
        assertTrue(sub.lastSemesterNoC());
        assertTrue(sub.lastSemesterOnlyFives());
    }
}
