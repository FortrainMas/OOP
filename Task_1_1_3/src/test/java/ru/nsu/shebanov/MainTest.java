package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Tests console interactions.
 * */
class MainTest {

    /**
     * The sing needed test.
     * */
    @Test
    void testMain() {
        String input = "((3+(2*x))+1)\nx\nx = 10";


        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);
        String res = out.toString().replaceAll("\\r\\n?", "\n");


        String expectedOutput =
                "((3+(2*x))+1)\n"
                        + "Expression: ((3+(2*x))+1)\n"
                        + "Derivative: ((0+((0*x)+(2*1)))+0)\n"
                        + "Assigned: 24.0\n"
                        + "Simplified: ((3+(2*x))+1)\n";
        assertEquals(expectedOutput, res);
        assertEquals(expectedOutput.length(), res.length());

        for (int i = 0; i < expectedOutput.length(); i++) {
            if (expectedOutput.charAt(i) != res.charAt(i)) {
                System.out.println(i + expectedOutput.charAt(i) + res.charAt(i));
            }
            assertEquals(expectedOutput.charAt(i), res.charAt(i));
        }

        System.setIn(System.in);
        System.setOut(System.out);
    }
}
