package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for getSimplified function
 */
public class SimplificationTest {

    @Test
    void SimplificationATest(){
        String expression = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String expected = "-0.5";
        String actual = RPN.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }

    @Test
    void SimplificationBCTest(){
        String expression = "(0+((0*x)+(2*1)))";
        String expected = "2";
        String actual = RPN.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }

    @Test
    void simplificationDTest(){
        String expression = "((2+4+x)-(3+3+x))";
        String expected = "0";
        String actual = RPN.getExpression(expression).getSimplified().toString();

        assertEquals(expected, actual);
    }
}
