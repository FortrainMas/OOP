package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivativesTest {
    @Test
    void basicExpressionTest(){
        String expression = "(2+x*3)*x";
        String expected = "(((0+((1*3)+(x*0)))*x)+((2+(x*3))*1))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    @Test
    void exampleDerivativeTest(){
        String expression = "(3+(2*x))";
        String expected = "(0+((0*x)+(2*1)))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    @Test
    void multipleVariablesTest(){
        String expression = "x+y*2";
        String expected = "(1+((0*2)+(y*0)))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }

    @Test
    void divisionDerivativeTest(){
        String expression = "(5-x)/2";
        String expected = "((((5-x)*0)+((0-1)*2))/(2*2))";
        String actual = RPN.getExpression(expression).getDerivative("x").toString();

        assertEquals(expected, actual);
    }
}
