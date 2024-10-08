package ru.nsu.shebanov.equations;

/** Class for handling division expressions. */
public class Div extends Expression {
    public Expression leftExpression;
    public Expression rightExpression;

    /**
     * Constructor for division.
     *
     * @param leftExpression left expression
     * @param rightExpression non-zero right expression
     */
    public Div(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;

        Expression simplifiedRight = rightExpression.getSimplified();
        if (simplifiedRight instanceof Number numb) {
            if (numb.value == 0) {
                throw new ArithmeticException("Division by zero");
            }
        }
    }

    /**
     * Estimates derivative for division expression.
     *
     * @param variable name of variable for derivative
     *
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Div(
                new Sub(
                        new Mult(this.leftExpression, this.rightExpression.getDerivative(variable)),
                        new Mult(
                                this.leftExpression.getDerivative(variable), this.rightExpression)),
                new Mult(this.rightExpression, this.rightExpression));
    }

    /**
     * Simplifies derivative expression.
     * firstly simplifies left and right expressions recursively if both
     * left and right expression of simplified division appears to be number returns their
     * difference as Number
     *
     * @return simplified Expression for division
     */
    @Override
    public Expression getSimplified() {
        Div simplifiedDiv =
                new Div(this.leftExpression.getSimplified(), this.rightExpression.getSimplified());
        if (simplifiedDiv.leftExpression instanceof Number leftNumber
                && simplifiedDiv.rightExpression instanceof Number rightNumber) {
            return new Number(
                    leftNumber.value
                            / rightNumber.value);
        } else if (this.leftExpression instanceof Number leftNumber
                && leftNumber.value == 0) {
            return new Number(0);
        } else {
            return simplifiedDiv;
        }
    }

    /**
     * Evaluate division expression.
     *
     * @param assignationString assignation for variables
     * @return evaluated expression
     */
    @Override
    public double eval(String assignationString) {
        double leftResult = this.leftExpression.eval(assignationString);
        double rightResult = this.rightExpression.eval(assignationString);

        if (rightResult == 0) {
            throw new ArithmeticException();
        }
        return leftResult / rightResult;
    }

    /**
     * Stringify division.
     *
     * @return (a/b)
     */
    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "/" + this.rightExpression.toString() + ")";
    }

    /**
     * Equals for division.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Div odiv) {
            return leftExpression.equals(odiv.leftExpression) &&
                    rightExpression.equals(odiv.rightExpression);
        }

        return false;
    }


    /**
     * Hash code for division.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int l = leftExpression.hashCode();
        int r = rightExpression.hashCode();
        return l + r + (l % r);
    }
}
