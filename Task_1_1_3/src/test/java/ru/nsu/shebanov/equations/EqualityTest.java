package ru.nsu.shebanov.equations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for equality.
 */
public class EqualityTest {

    /**
     * Equal expressions.
     */
    @Test
    void basicEqual() {
        Expression exp1 = Notation.getExpression("(2+x*3)*x");
        Expression exp2 = Notation.getExpression("(2+x*3)*x");

        assertEquals(exp1, exp2);
    }

    /**
     * Not equal expression.
     */
    @Test
    void basicNotEqual() {
        Expression exp1 = Notation.getExpression("(2+x*4)*x");
        Expression exp2 = Notation.getExpression("(2+x*3)*x");

        assertNotEquals(exp1, exp2);
    }

    /**
     * Complicated equal expression.
     */
    @Test
    void hardEqual() {
        Expression exp1 = Notation.getExpression("78+36*23+1/7-21+43*12-90*120/12/12/12*123-12");
        Expression exp2 = Notation.getExpression("78+36*23+1/7-21+43*12-90*120/12/12/12*123-12");

        assertEquals(exp1, exp2);
    }
}
