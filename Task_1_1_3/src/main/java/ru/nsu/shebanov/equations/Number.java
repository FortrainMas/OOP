package ru.nsu.shebanov.equations;


/**
 * Class for handling number constants.
 */
public class Number extends Expression {
    public double value;

    /**
     * Constructor for number.
     *
     * @param value value to save as number
     */
    public Number(double value) {
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
     * Just returns its value.
     *
     * @param assignationString doesn't play a role
     *
     * @return number's value
     */
    @Override
    public double eval(String assignationString) {
        return this.value;
    }

    /**
     * Stringify number.
     *
     * @return number
     */
    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return "" + (int) this.value;
        } else {
            return "" + this.value;
        }
    }

    /**
     * Equals for numbers.
     *
     * @param o another object
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if(o instanceof Number obj) {
            return value == obj.value;
        }

        return false;
    }


    /**
     * Hash code for number.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
