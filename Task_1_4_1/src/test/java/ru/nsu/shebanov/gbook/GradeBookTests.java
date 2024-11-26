package ru.nsu.shebanov.gbook;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
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

}
