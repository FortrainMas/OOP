package ru.nsu.shebanov.gbook;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTests {
    @Test
    void basicBookTest () {
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
        List<Subject> ls = new ArrayList<>();
        ls.add(sub);
        GradeBook gb = new GradeBook(ls);

        gb.setMark("Кораблестроение", 3, "контрольная", 2);
        gb.setMark("Кораблестроение", 3, "контрольная", 2);
        gb.setMark("Кораблестроение", 3, "коллоквиум", 2);
        gb.setMark("Кораблестроение", 3, "контрольная", 2);
        gb.setMark("Кораблестроение", 3, "задание", 2);

        gb.setMark("Кораблестроение", 4, "контрольная", 2);
        gb.setMark("Кораблестроение", 4, "контрольная", 2);
        gb.setMark("Кораблестроение", 4, "коллоквиум", 2);
        gb.setMark("Кораблестроение", 4, "контрольная", 2);
        gb.setMark("Кораблестроение", 4, "задание", 2);


        gb.setMark("Кораблестроение", 5, "контрольная", 5);
        gb.setMark("Кораблестроение", 5, "контрольная", 5);
        gb.setMark("Кораблестроение", 5, "коллоквиум", 5);
        gb.setMark("Кораблестроение", 5, "контрольная", 5);
        gb.setMark("Кораблестроение", 5, "задание", 5);


        int results = gb.determineLastSemester();
        int expected = 5;
        
        assertEquals(results, expected);

        boolean canTransfer = gb.isAllowedToTransfer();
        boolean cantTransferExpected = true;
        assertEquals(canTransfer, cantTransferExpected);

        boolean canHighScholarship = gb.isHighScholarship();
        boolean canHighScholarshipExpected = true;
        assertEquals(canHighScholarship, canHighScholarshipExpected);

        double av = gb.averageMark();
        double avExpected = 3.0;
        assertEquals(av, avExpected);

        boolean canHonors = gb.diplomaHonors();
        boolean canHonorsExpected = true;
        assertEquals(canHonors, canHonorsExpected);
    }
}
