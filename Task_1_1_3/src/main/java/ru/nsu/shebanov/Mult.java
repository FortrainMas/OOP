package ru.nsu.shebanov;

/**
 * Class for handling multiplication expressions.
 */
public class Mult extends Expression {
    Expression leftExpression;
    Expression rightExpression;

    Mult(Expression leftExpression, Expression rightExpression) {
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
        if (simplifiedMult.leftExpression instanceof Number
                && simplifiedMult.rightExpression instanceof Number) {
            return new Number(
                    ((Number) simplifiedMult.leftExpression).value
                            * ((Number) simplifiedMult.rightExpression).value);
        } else if (simplifiedMult.leftExpression instanceof Number
                        && ((Number) simplifiedMult.leftExpression).value == 0
                || simplifiedMult.rightExpression instanceof Number
                        && ((Number) simplifiedMult.rightExpression).value == 0) {
            return new Number(0);
        } else if (simplifiedMult.leftExpression instanceof Number
                && ((Number) simplifiedMult.leftExpression).value == 1) {
            return simplifiedMult.rightExpression;
        } else if (simplifiedMult.rightExpression instanceof Number
                && ((Number) simplifiedMult.rightExpression).value == 1) {
            return simplifiedMult.leftExpression;
        } else {
            return simplifiedMult;
        }
    }

    /**
     * Evaluate multiplication expression.
     *
     * @param assignationString assignation for variables
     * @return
     */
    @Override
    public double eval(String assignationString) {
        return this.leftExpression.eval(assignationString)
                * this.rightExpression.eval(assignationString);
    }

    @Override
    public String toString() {
        return "(" + this.leftExpression.toString() + "*" + this.rightExpression.toString() + ")";
    }
}
