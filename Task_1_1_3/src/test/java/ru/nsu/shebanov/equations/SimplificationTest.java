package ru.nsu.shebanov.equations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for getSimplified function.
 */
public class SimplificationTest {

    /**
     * Good test case.
     * It covers A, B, C conditions for simplification
     */
    @Test
    void simplificationTestA() {
        String expression = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String expected = "-0.5";
        String actual = Notation.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }

    /**
     * Test which covers B and C conditions.
     * */
    @Test
    void simplificationTestB() {
        String expression = "(0+((0*x)+(2*1)))";
        String expected = "2";
        String actual = Notation.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }

    /**
     * Test case for D condition.
     * */
    @Test
    void simplificationTestD() {
        String expression = "((2+4+x)-(3+3+x))";
        String expected = "0";
        String actual = Notation.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }
}
