package ru.nsu.shebanov;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RPNTest {

    @Test
    void longRPNTest(){
        String expression = "78+36*23+1/7-21+43*12-90*120/12/12/12*123-12";
        ArrayList<String> expected =
                new ArrayList<>(
                        Arrays.asList(
                                "78", "36", "23", "*", "+", "1", "7", "/", "+", "21", "-", "43",
                                "12", "*", "+", "90", "120", "*", "12", "/", "12", "/", "12", "/",
                                "123", "*", "-", "12", "-"));
        ArrayList<String> actual = RPN.getRPN(expression);
        assertEquals(expected, actual);
    }

    @Test
    void RPNWithBracketsTest() {
        String expression = "a*(b+c)";
        ArrayList<String> expected =
                new ArrayList<>(
                        Arrays.asList(
                                "a", "b", "c", "+", "*"));
        ArrayList<String> actual = RPN.getRPN(expression);
        assertEquals(expected, actual);
    }

    @Test
    void RPNWithLongVariablesTest(){
        String expression = "very*(long+names)";
        ArrayList<String> expected =
                new ArrayList<>(
                        Arrays.asList(
                                "very", "long", "names", "+", "*"));
        ArrayList<String> actual = RPN.getRPN(expression);
        assertEquals(expected, actual);
    }

    @Test
    void RPNExpressionTest(){

        String expression = "very*(long+names)";
        String expected = "(very*(long+names))";
        String actual = RPN.getExpression(expression).toString();

        assertEquals(expected, actual);
    }

    @Test
    void complicatedExpressionTest() {
        String expression = "x*2+(3*3)/2+1-3+10*(((28+3)*2)/2)";
        String expected = "(((((x*2)+((3*3)/2))+1)-3)+(10*(((28+3)*2)/2)))";
        String actual = RPN.getExpression(expression).toString();

        assertEquals(expected, actual);
    }


}
