package ru.nsu.shebanov.equations;

/** Class for handling sum expressions. */
public class Sum extends Expression {

    public Expression leftExpression;
    public Expression rightExpression;

    /**
     * Constructor for sum.
     *
     * @param leftExpression left expression
     * @param rightExpression right expression
     */
    public Sum(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Estimates derivative for sum expression.
     *
     * @param variable name of variable for derivative
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Sum(
                this.leftExpression.getDerivative(variable),
                this.rightExpression.getDerivative(variable));
    }

    /**
     * Simplifies add expression. firstly simplifies left and right expressions recursively if both
     * left and right expression of simplified sum appears to be number returns their sum as Number
     *
     * @return simplified Expression for sum
     */
    @Override
    public Expression getSimplified() {
        Sum simplifiedSum =
                new Sum(this.leftExpression.getSimplified(), this.rightExpression.getSimplified());
        if (simplifiedSum.leftExpression instanceof Number leftNumber
                && simplifiedSum.rightExpression instanceof Number rightNumber) {
            return new Number(
                    leftNumber.value
                            + rightNumber.value);
        } else {
            return simplifiedSum;
        }
    }

    /**
     * Evaluate sum expression.
     *
     * @param assignationString formated assignation string
     * @return evaluated expression
     */
    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                + this.rightExpression.eval(assignationString);
    }

    /**
     * Stringify sum.
     *
     * @return (a+b)
     */
    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "+" + this.rightExpression.toString() + ")";
    }

    /**
     * Equals for sum.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Sum obj) {
            return leftExpression.equals(obj.leftExpression) &&
                    rightExpression.equals(obj.rightExpression);
        }

        return false;
    }


    /**
     * Hash code for sum.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int l = leftExpression.hashCode();
        int r = rightExpression.hashCode();

        return l + r + (l + r);
    }
}
