package ru.nsu.shebanov.z;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class ZFunctionTests {
    @Test
    void basicTest(){
        String str = "abracadabra";
        String pattern = "bra";

        List<Integer> res = Zfunction.find(str, pattern);
        List<Integer> res2 = new ArrayList<>();
        res2.add(1);
        res2.add(8);

        System.out.println(res);

        assertEquals(res, res2);
    }


    @Test
    void hungarianTest(){
        String str1 = "Inkább magyar, mint Java";
        String pattern = "agy";

        List<Integer> res = Zfunction.find(str1, pattern);

        List<Integer> expected = new ArrayList<>();
        expected.add(8);

        assertEquals(res, expected);
    }

    @Test
    void testUsingResources() throws URISyntaxException {
        String fileName = "myText.txt";
        String pattern = "ИТЕ";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(10L);
        expected.add(57L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingBigFile() throws IOException {
        String filePath = "testFile.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < 100; i++) {
                writer.write("АЧЁОН".repeat(300) + "?");
            }
        }
        List<Long> res = Zfunction.findInFile(filePath, "?");
        assertEquals(res.size(), 100);
        new File(filePath).delete();
    }
}