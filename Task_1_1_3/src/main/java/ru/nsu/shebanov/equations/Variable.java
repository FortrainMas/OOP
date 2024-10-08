package ru.nsu.shebanov.equations;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class for handling Variable expressions. */
public class Variable extends Expression {
    public String variableName;

    /**
     * Parse assigned variables.
     *
     * @param input string of variables
     *
     * @return hashmap of variables
     */
    private static Map<String, Integer> parseAssignations(String input) {
        Map<String, Integer> dictionary = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*(\\d+)(;?)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));
            dictionary.put(key, value);
        }

        return dictionary;
    }

    /**
     * Constructor for variable.
     *
     * @param name name of variable
     */
    public Variable(String name) {
        this.variableName = name;
    }

    /**
     * Estimates variable derivative.
     *
     * @param variable variable to get derivative
     *
     * @return 1 if same derivative, 0 otherwise
     */
    @Override
    public Expression getDerivative(String variable) {
        if (variable.equals(this.variableName)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Simplifies variable.
     *
     * @return returns new but exactly the same variable
     */
    @Override
    public Expression getSimplified() {
        return new Variable(this.variableName);
    }

    /**
     * Evaluate the expression.
     *
     * @param assignationString formated assignation string
     *
     * @return evaluated expression
     */
    @Override
    public double eval(String assignationString) {
        Map<String, Integer> assignations = parseAssignations(assignationString);

        return assignations.get(this.variableName);
    }


    /**
     * Stringify expression.
     *
     * @return variable
     */
    @Override
    public String toString() {
        return this.variableName;
    }


    /**
     * Equals for variable.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if(o instanceof Variable obj) {
            return variableName.equals(obj.variableName);
        }

        return false;
    }


    /**
     * Hash code for variable.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return variableName.hashCode();
    }
}
