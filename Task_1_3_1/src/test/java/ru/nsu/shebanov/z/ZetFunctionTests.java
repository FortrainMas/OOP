package ru.nsu.shebanov.z;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ZetFunctionTests {
    @Test
    void basicTest() {
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
    void edgeTest() throws URISyntaxException {
        String fileName = "edge.txt";
        String pattern = "edge test";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern, 10);

        List<Long> expected = new ArrayList<>();
        expected.add(5L);

        assertEquals(res, expected);
    }

    @Test
    void hungarianTest() {
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
        expected.add(58L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingBigFile() throws IOException {
        String filePath = "testFile.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < 1000000; i++) {
                writer.write("АЧЁОН".repeat(300) + "?");
            }
        }
        List<Long> res = Zfunction.findInFile(filePath, "?");
        assertEquals(res.size(), 1000000);
        (new File(filePath)).delete();
    }
}