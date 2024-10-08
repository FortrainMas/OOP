package ru.nsu.shebanov.equations;

/**
 * Class for handling multiplication expressions.
 */
public class Mult extends Expression {
    public Expression leftExpression;
    public Expression rightExpression;

    /**
     * Constructor for multiplication.
     *
     * @param leftExpression left expression
     * @param rightExpression right expression
     */
    public Mult(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Estimates derivative for multiplication expression.
     *
     * @param variable name of variable for derivative
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Sum(
                new Mult(this.leftExpression.getDerivative(variable), this.rightExpression),
                new Mult(this.leftExpression, this.rightExpression.getDerivative(variable)));
    }

    /**
     * Simplifies multiplication expression.
     * firstly simplifies left and right expressions
     * recursively if both left and right expression of simplified multiplication appears to be
     * number returns their multiplication as Number if one of them is zero, returns 0 if one of the
     * is one, return another one
     *
     * @return simplified Expression for multiplication
     */
    @Override
    public Expression getSimplified() {
        Mult simplifiedMult =
                new Mult(this.leftExpression.getSimplified(), this.rightExpression.getSimplified());
        if (simplifiedMult.leftExpression instanceof Number leftNumber
                && simplifiedMult.rightExpression instanceof Number rightNumber) {
            return new Number(
                    leftNumber.value
                            * rightNumber.value);
        } else if (simplifiedMult.leftExpression instanceof Number leftNumber
                && leftNumber.value == 0
                || simplifiedMult.rightExpression instanceof Number rightNumber
                && rightNumber.value == 0) {
            return new Number(0);
        } else if (simplifiedMult.leftExpression instanceof Number leftNumber
                && leftNumber.value == 1) {
            return simplifiedMult.rightExpression;
        } else if (simplifiedMult.rightExpression instanceof Number rightNumber
                && rightNumber.value == 1) {
            return simplifiedMult.leftExpression;
        } else {
            return simplifiedMult;
        }
    }

    /**
     * Evaluate multiplication expression.
     *
     * @param assignationString assignation for variables
     * @return evaluated expression
     */
    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                * this.rightExpression.eval(assignationString);
    }

    /**
     * Stringify multiplication.
     *
     * @return a * b
     */
    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "*" + this.rightExpression.toString() + ")";
    }


    /**
     * Equals for multiplications.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Mult omult) {
            return leftExpression.equals(omult.leftExpression)
                    && rightExpression.equals(omult.rightExpression);
        }

        return false;
    }


    /**
     * Hash code for multiplication.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int l = leftExpression.hashCode();
        int r = rightExpression.hashCode();
        return l + r + (l / r);
    }
}
