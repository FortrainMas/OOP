package ru.nsu.shebanov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for eval function.
 */
public class EvalTest {

    /** Simple eval test.
     * Checks only the most basic case
     */
    @Test
    void simpleEvalTest() {
        String expression = "2+x";
        double expectation = 5;
        double actually = Notation.getExpression(expression).eval("x = 3");

        assertEquals(expectation, actually);
    }

    /** Complicated test for eval.
     * This test checks all signs and consists of many variables
     */
    @Test
    void manyVariablesTest() {
        String expression = "(x+y-long)*story/short";
        double expectation = 10;
        double actually =
                Notation.getExpression(expression)
                        .eval("x = 3; y = 8; long = 1; story = 2; short = 2");

        assertEquals(expectation, actually);
    }

    /**
     * Test which proofs that eval work with nested expressions.
     * */
    @Test
    void hardEvalTest() {
        String expression = "(((x+y)-2+x)*z)*3";
        double expectation = 180;
        double actually = Notation.getExpression(expression).eval("x = 3; y = 8; z = 5");

        assertEquals(expectation, actually);
    }
}
