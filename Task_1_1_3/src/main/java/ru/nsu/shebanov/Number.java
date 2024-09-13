package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Dictionary;

public class Number extends Expression{
    double value;

    Number(double value){
        super();
        this.value = value;
    }

    @Override
    public Number getDerivative(String variable){
        return new Number(0);
    }

    @Override
    public Number getSimplified(){
        return new Number(this.value);
    }

    @Override
    public double eval(String assignationString){
        return this.value;
    }

    @Override
    public String toString(){
        if (this.value % 1 == 0){
            return "" + (int)this.value;
        }else{
            return "" + this.value;
        }
    }
}
