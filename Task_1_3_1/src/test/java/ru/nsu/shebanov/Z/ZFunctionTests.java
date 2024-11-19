package ru.nsu.shebanov.Z;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        List<Integer> res = ZFunction.find(str, pattern);
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

        List<Integer> res = ZFunction.find(str1, pattern);

        List<Integer> expected = new ArrayList<>();
        expected.add(8);

        assertEquals(res, expected);
    }

    @Test
    void testUsingEnglishResources()  throws URISyntaxException {
        String fileName = "Russian.txt";
        String pattern = "Stupid";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(0L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingChineseResources()  throws URISyntaxException {
        String fileName = "SongTao.txt";
        String pattern = "在";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(2L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingEmojiResources()  throws URISyntaxException {
        String fileName = "emoji.txt";
        String pattern = "\uD83D\uDCA5";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(12L);
        expected.add(14L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingResources() throws URISyntaxException {
        String fileName = "myText.txt";
        String pattern = "ИТЕ";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(10L);
        expected.add(57L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingBigFile() throws IOException {
        String filePath = "testFile.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < 1_000_000; i++) {
                writer.write("Джава");
            }
        }
        //JavaJavaJavaJavaJavaJavaJavaJavaJavaJava
        List<Long> res = ZFunction.findInFile(filePath, "ва");
        assertEquals(1_000_000, res.size());
        new File(filePath).delete();
    }
}