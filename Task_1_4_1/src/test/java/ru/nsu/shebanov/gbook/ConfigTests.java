package ru.nsu.shebanov.gbook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

class ConfigTests {
    @Test
    void basicTest() {
        GradeBook gb;
        try {
            File resource = new File(
                    getClass().getClassLoader().getResource("program.txt").toURI());
            gb = GradeBook.parseGradeBook(resource.getPath());
            System.out.println(gb);
        } catch (IOException | URISyntaxException e) {
            fail();
            return;
        }

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

    }
}
