package ru.nsu.shebanov.equations;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Tests for possible exceptions.
 */
public class ExceptionsTest {

    /**
     * Basic case.
     */
    @Test
    void incorrectStatement() {
        String expression = "1/(0+0)";

        try {
            Notation.getExpression(expression);
        } catch (ArithmeticException e) {
            return;
        }

        fail();
    }

    /**
     * Incorrect eval statement.
     * */
    @Test
    void evalIncorrectStatement() {
        String expression = "1/(x+0)";

        try {
            System.out.println(Notation.getExpression(expression).eval("x=0"));
        } catch (ArithmeticException e) {
            return;
        }
        fail();
    }

    /**
     * Mismatched parentheses.
     * */
    @Test
    void mismatchedParentheses1() {
        String expression = "((2)))";
        try {
            Notation.getExpression(expression);
        } catch (IllegalArgumentException e) {
            return;
        }

        fail();
    }

    /**
     * Mismatched parentheses.
     * */
    @Test
    void mismatchedParentheses2() {
        String expression = "(((2))";
        try {
            Notation.getExpression(expression);
        } catch (IllegalArgumentException e) {
            return;
        }

        fail();
    }


}
