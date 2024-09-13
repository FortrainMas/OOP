package ru.nsu.shebanov;


/**
 * Class for handling number constants.
 */
public class Number extends Expression {
    double value;

    Number(double value) {
        super();
        this.value = value;
    }

    /**
     * Always is zero.
     *
     * @param variable gets variable name
     *
     * @return 0
     */
    @Override
    public Number getDerivative(String variable) {
        return new Number(0);
    }

    /**
     * Always returns itself copied.
     *
     * @return new Number(this.value)
     */
    @Override
    public Number getSimplified() {
        return new Number(this.value);
    }

    /**
     * just returns its value.
     *
     * @param assignationString doesn't play a role
     *
     * @return number's value
     */
    @Override
    public double eval(String assignationString) {
        return this.value;
    }

    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value;
        } else {
            return "" + this.value;
        }
    }
}
