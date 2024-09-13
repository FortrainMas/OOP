package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Tests for reverse polish notation and string input parsing.
 */
public class RPNTest {

    /**
     * Test of RPN with big input.
     */
    @Test
    void longNotationTest() {
        String expression = "78+36*23+1/7-21+43*12-90*120/12/12/12*123-12";
        ArrayList<String> expected =
                new ArrayList<>(
                        Arrays.asList(
                                "78", "36", "23", "*", "+", "1", "7", "/", "+", "21", "-", "43",
                                "12", "*", "+", "90", "120", "*", "12", "/", "12", "/", "12", "/",
                                "123", "*", "-", "12", "-"));
        ArrayList<String> actual = RPN.getReversePolish(expression);
        assertEquals(expected, actual);
    }

    /**
     * Test of RPN without brackets.
     */
    @Test
    void notationWithBracketsTest() {
        String expression = "a*(b+c)";
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("a", "b", "c", "+", "*"));
        ArrayList<String> actual = RPN.getReversePolish(expression);
        assertEquals(expected, actual);
    }

    /**
     * Check for long variable names handling.
     */
    @Test
    void notationWithLongVariablesTest() {
        String expression = "very*(long+names)";
        ArrayList<String> expected =
                new ArrayList<>(Arrays.asList("very", "long", "names", "+", "*"));
        ArrayList<String> actual = RPN.getReversePolish(expression);
        assertEquals(expected, actual);
    }

    /**
     * Check how expressions get build from RPN.
     */
    @Test
    void notationExpressionTest() {

        String expression = "very*(long+names)";
        String expected = "(very*(long+names))";
        String actual = RPN.getExpression(expression).toString();

        assertEquals(expected, actual);
    }

    /**
     * Tests for complicated expressions.
     */
    @Test
    void complicatedExpressionTest() {
        String expression = "x*2+(3*3)/2+1-3+10*(((28+3)*2)/2)";
        String expected = "(((((x*2)+((3*3)/2))+1)-3)+(10*(((28+3)*2)/2)))";
        String actual = RPN.getExpression(expression).toString();

        assertEquals(expected, actual);
    }
}
