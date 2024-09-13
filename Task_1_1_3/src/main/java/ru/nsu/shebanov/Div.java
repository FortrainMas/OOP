package ru.nsu.shebanov;

/** Class for handling division expressions. */
public class Div extends Expression {
    Expression leftExpression;
    Expression rightExpression;

    Div(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    /**
     * Estimates derivative for division expression.
     *
     * @param variable name of variable for derivative
     * @return derivative
     */
    @Override
    public Expression getDerivative(String variable) {
        return new Div(
                new Sum(
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
        if (simplifiedDiv.leftExpression instanceof Number
                && simplifiedDiv.rightExpression instanceof Number) {
            return new Number(
                    ((Number) simplifiedDiv.leftExpression).value
                            / ((Number) simplifiedDiv.rightExpression).value);
        } else if (this.leftExpression instanceof Number
                && ((Number) this.leftExpression).value == 0) {
            return new Number(0);
        } else {
            return simplifiedDiv;
        }
    }

    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                / this.rightExpression.eval(assignationString);
    }

    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "/" + this.rightExpression.toString() + ")";
    }
}
