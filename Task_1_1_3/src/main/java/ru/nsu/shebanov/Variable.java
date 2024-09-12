package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Class for handling Variable expressions.
 */
public class Variable extends Expression{
    public String variableName;

    Variable(String name){
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

    @Override
    public double eval(String assignationString){
        Dictionary<String, Integer> assignations = parseAssignations(assignationString);

        return assignations.get(this.variableName);
    }

    @Override
    public String toString(){
        return this.variableName;
    }
}
