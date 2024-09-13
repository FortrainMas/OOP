package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for getDerivative function.
 */
public class DerivativesTest {

    /**
     * Not complicated example.
     */
    @Test
    void basicExpressionTest() {
        String expression = "(2+x*3)*x";
        String expected = "(((0+((1*3)+(x*0)))*x)+((2+(x*3))*1))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    /**
     * Example from doc file.
     */
    @Test
    void exampleDerivativeTest() {
        String expression = "(3+(2*x))";
        String expected = "(0+((0*x)+(2*1)))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    /**
     * Test for multiple variables.
     */
    @Test
    void multipleVariablesTest() {
        String expression = "x+y*2";
        String expected = "(1+((0*2)+(y*0)))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    /**
     * Division derivative test.
     */
    @Test
    void divisionDerivativeTest() {
        String expression = "(5-x)/2";
        String expected = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }
}
