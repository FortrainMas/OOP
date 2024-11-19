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

<<<<<<< HEAD
        List<Integer> res = Zfunction.find(str, pattern);
=======
        List<Integer> res = ZFunction.find(str, pattern);
>>>>>>> 7c13b72 (More useful fixes)
        List<Integer> res2 = new ArrayList<>();
        res2.add(1);
        res2.add(8);

        System.out.println(res);

        assertEquals(res, res2);
    }


    @Test
    void hungarianTest() {
        String str1 = "Inkább magyar, mint Java";
        String pattern = "agy";

<<<<<<< HEAD
        List<Integer> res = Zfunction.find(str1, pattern);
=======
        List<Integer> res = ZFunction.find(str1, pattern);
>>>>>>> 7c13b72 (More useful fixes)

        List<Integer> expected = new ArrayList<>();
        expected.add(8);

        assertEquals(res, expected);
    }

    @Test
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> 7c13b72 (More useful fixes)
    void testUsingEnglishResources() throws URISyntaxException {
        String fileName = "Russian.txt";
        String pattern = "Stupid";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


<<<<<<< HEAD
        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern);
=======
        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);
>>>>>>> 7c13b72 (More useful fixes)

        List<Long> expected = new ArrayList<>();
        expected.add(0L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingChineseResources() throws URISyntaxException {
        String fileName = "SongTao.txt";
        String pattern = "在";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


<<<<<<< HEAD
        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern);
=======
        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);
>>>>>>> 7c13b72 (More useful fixes)

        List<Long> expected = new ArrayList<>();
        expected.add(2L);

        assertEquals(res, expected);
    }

    @Test
    void testUsingEmojiResources() throws URISyntaxException {
        String fileName = "emoji.txt";
<<<<<<< HEAD
        String pattern = "\uD83D\uDC79";
=======
        String pattern = "\uD83D\uDCA5";
>>>>>>> 7c13b72 (More useful fixes)

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


<<<<<<< HEAD

        List<Long> expected = new ArrayList<>();
        expected.add(4L);
        expected.add(6L);
        expected.add(8L);
        expected.add(10L);
        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern);
=======
        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);

        List<Long> expected = new ArrayList<>();
        expected.add(12L);
        expected.add(14L);
>>>>>>> 7c13b72 (More useful fixes)

        assertEquals(res, expected);
    }

    @Test
<<<<<<< HEAD
=======
>>>>>>> e699772 (MORE FIXES)
>>>>>>> 7c13b72 (More useful fixes)
    void testUsingResources() throws URISyntaxException {
        String fileName = "myText.txt";
        String pattern = "ИТЕ";

        File resource = new File(
                getClass().getClassLoader().getResource(fileName).toURI());


<<<<<<< HEAD
        List<Long> res = Zfunction.findInFile(resource.getPath(), pattern);
=======
        List<Long> res = ZFunction.findInFile(resource.getPath(), pattern);
>>>>>>> 7c13b72 (More useful fixes)

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
<<<<<<< HEAD
                writer.write("Джава");
            }
        }
        //JavaJavaJavaJavaJavaJavaJavaJavaJavaJava
        List<Long> res = Zfunction.findInFile(filePath, "ва");
=======
                writer.write("Java");
            }
        }
        //JavaJavaJavaJavaJavaJavaJavaJavaJavaJava
        List<Long> res = ZFunction.findInFile(filePath, "va");
        System.out.println(res);
>>>>>>> 7c13b72 (More useful fixes)
        assertEquals(1_000_000, res.size());
        new File(filePath).delete();
    }
}
