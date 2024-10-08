package ru.nsu.shebanov.equations;

import java.util.Scanner;

/**
 * Class handling one console demonstration.
 */
public class Main {
    /**
     * The main functions.
     *
     * @param args String[] args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String infix = scanner.nextLine();
        Expression expression = Notation.getExpression(infix);
        expression.print();
        System.out.println("Expression: " + expression);
        String variable = scanner.nextLine();
        System.out.println("Derivative: " + expression.getDerivative(variable));
        String assignation = scanner.nextLine();
        System.out.println("Assigned: " + expression.eval(assignation));
        System.out.println("Simplified: " + expression.getSimplified());

        scanner.close();
    }
}