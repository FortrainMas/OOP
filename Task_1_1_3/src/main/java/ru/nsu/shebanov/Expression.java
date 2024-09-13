package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class expression.
 */
public abstract class Expression {

    /**
     * Abstract method to get new expression which is derivative of original.
     *
     * @param variable variable for derivative
     * @return new expression
     */
    public abstract Expression getDerivative(String variable);

    /**
     * Abstract method to get new expression which is simplification of original.
     *
     * @return new expression
     */
    public abstract Expression getSimplified();


    /**
     * Abstract method to eval Expression with assigned variables.
     *
     * @param assignation formated assignation string
     * @return result of evaluation
     */
    public abstract double eval(String assignation);

    /**
     * Print expression to console.
     */
    public void print() {
        System.out.println(this.toString());
    }
}
