package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Expression {
    public abstract Expression getDerivative(String variable);
    public abstract Expression getSimplified();
    public abstract double eval(String assignation);

    public void print() {
        System.out.println(this.toString());
    }
}
