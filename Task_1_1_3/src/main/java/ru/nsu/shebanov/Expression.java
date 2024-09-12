package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Expression {
    public static Dictionary<String, Integer> parseAssignations(String input) {
        Dictionary<String, Integer> dictionary = new Hashtable<>();
        Pattern pattern = Pattern.compile("(\\w+)\\s*=\\s*(\\d+);");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String key = matcher.group(1);
            int value = Integer.parseInt(matcher.group(2));
            dictionary.put(key, value);
        }

        return dictionary;
    }

    public abstract Expression getDerivative(String variable);
    public abstract Expression getSimplified();
    public abstract double eval(String assignation);
}
