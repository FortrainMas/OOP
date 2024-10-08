package ru.nsu.shebanov.equations;

/**
 * Class for handling sub expressions.
 */
public class Sub extends Expression {
    public Expression leftExpression;
    public Expression rightExpression;

    /**
     * Constructor for sub.
     *
     * @param leftExpression left expression
     * @param rightExpression right expression
     */
    public Sub(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Estimates derivative for sub expression.
     *
     * @param variable name of variable for derivative
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Sub(
                this.leftExpression.getDerivative(variable),
                this.rightExpression.getDerivative(variable));
    }

    /**
     * Simplifies sub expression. firstly simplifies left and right expressions recursively if both
     * left and right expression of simplified difference appears to be number returns their
     * difference as Number
     *
     * @return simplified Expression for sub
     */
    @Override
    public Expression getSimplified() {
        Sub simplifiedSub =
                new Sub(this.leftExpression.getSimplified(), this.rightExpression.getSimplified());
        if (simplifiedSub.leftExpression instanceof Number leftNumber
                && simplifiedSub.rightExpression instanceof Number rightNumber) {
            return new Number(
                    leftNumber.value
                            - rightNumber.value);
        } else if (simplifiedSub
                .leftExpression
                .toString()
                .equals(simplifiedSub.rightExpression.toString())) {
            return new Number(0);
        } else {
            return simplifiedSub;
        }
    }

    /**
     * Evaluate sub expression.
     *
     * @param assignationString formated assignation string
     * @return evaluated expression.
     */
    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                - this.rightExpression.eval(assignationString);
    }

    /**
     * Stringify sub expression.
     *
     * @return (a-b)
     */
    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "-" + this.rightExpression.toString() + ")";
    }


    /**
     * Equals for sub.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Sub obj) {
            return leftExpression.equals(obj.leftExpression) &&
                    rightExpression.equals(obj.rightExpression);
        }

        return false;
    }


    /**
     * Hash code for sub.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int l = leftExpression.hashCode();
        int r = rightExpression.hashCode();

        return l + r + (l - r);
    }
}
