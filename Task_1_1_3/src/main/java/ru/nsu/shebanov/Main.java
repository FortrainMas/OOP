package ru.nsu.shebanov;

import java.util.Scanner;

/**
 * Class handling one console demonstration
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String infix = scanner.nextLine();
        Expression expression = RPN.getExpression(infix);
        expression.print();
        System.out.println("Expression: " + expression.toString());
        String variable = scanner.nextLine();
        System.out.println("Derivative: " + expression.getDerivative(variable).toString());
        String assignation = scanner.nextLine();
        System.out.println("Assigned: " + expression.eval(assignation));
        System.out.println("Simplified: " + expression.getSimplified().toString());

        scanner.close();
    }
}