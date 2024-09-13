package ru.nsu.shebanov;

/**
 * Class for handling sub expressions.
 */
public class Sub extends Expression {
    Expression leftExpression;
    Expression rightExpression;

    Sub(Expression leftExpression, Expression rightExpression) {
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
        if (simplifiedSub.leftExpression instanceof Number
                && simplifiedSub.rightExpression instanceof Number) {
            return new Number(
                    ((Number) simplifiedSub.leftExpression).value
                            - ((Number) simplifiedSub.rightExpression).value);
        } else if (simplifiedSub
                .leftExpression
                .toString()
                .equals(simplifiedSub.rightExpression.toString())) {
            return new Number(0);
        } else {
            return simplifiedSub;
        }
    }

    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                - this.rightExpression.eval(assignationString);
    }

    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "-" + this.rightExpression.toString() + ")";
    }
}
