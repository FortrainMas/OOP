package ru.nsu.shebanov;


import java.util.Dictionary;

/**
 * Class for handling sum expressions.
 */
public class Sum extends Expression {

    Expression leftExpression;
    Expression rightExpression;

    Sum(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Estimates derivative for sum expression.
     *
     * @param variable name of variable for derivative
     *
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Sum(
                this.leftExpression.getDerivative(variable),
                this.rightExpression.getDerivative(variable));
    }

    /**
     * Simplifies add expression.
     * firstly simplifies left and right expressions recursively
     * if both left and right expression of simplified sum appears to be number returns their sum as Number
     *
     * @return simplified Expression for sum
     */
    @Override
    public Expression getSimplified() {
        Sum simplifiedSum =
                new Sum(this.leftExpression.getSimplified(), this.rightExpression.getSimplified());
        if (simplifiedSum.leftExpression instanceof Number
                && simplifiedSum.rightExpression instanceof Number) {
            return new Number(
                    ((Number) simplifiedSum.leftExpression).value
                            + ((Number) simplifiedSum.rightExpression).value);
        } else {
            return simplifiedSum;
        }
    }

    @Override
    public double eval(String assignationString){
        return this.leftExpression.eval(assignationString) + this.rightExpression.eval(assignationString);
    }

    @Override
    public String toString(){
        return "("+this.leftExpression.toString()+"+"+this.rightExpression.toString()+")";
    }
}
