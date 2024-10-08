package ru.nsu.shebanov.equations;

import java.util.*;

/**
 * Class which uses reverse polish notation to produce original expression.
 */
public class Notation {

    /**
     * Checks if coming value is operator in math.
     *
     * @param c char value
     *
     * @return is the value an operator
     */
    private static boolean isOperator(char c){
        return (c == '+'
                || c == '-'
                || c == '*'
                || c == '/'
                || c == '('
                || c == ')');
    }

    /**
     * Gets the first token in the string from needed position.
     * token is number, variable name or
     * operation sign
     *
     * @param exp infix expression
     * @param pos position on which start to find tokens
     *
     * @return first found token
     */
    private static String getNextToken(String exp, int pos) {
        char firstSymbol = exp.charAt(pos);
        if (isOperator(firstSymbol)) {
            return "" + firstSymbol;
        } else if (Character.isDigit(firstSymbol)) {
            StringBuilder result = new StringBuilder();
            do {
                result.append(exp.charAt(pos));
                pos += 1;
            } while (pos < exp.length() && Character.isDigit(exp.charAt(pos)));
            return result.toString();
        } else {
            StringBuilder result = new StringBuilder();
            do {
                result.append(exp.charAt(pos));
                pos += 1;
            } while (pos < exp.length()
                    && !Character.isDigit(exp.charAt(pos))
                    && !isOperator(exp.charAt(pos)));
            return result.toString();
        }
    }

    /**
     * Produce array of reverse polish notations tokens.
     *
     * @param exp infix string
     *
     * @return postfix array of tokens
     */
    public static List<String> getReversePolish(String exp) {
        List<String> output = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        int position = 0;
        while (position != exp.length()) {
            String token = getNextToken(exp, position);
            position += token.length();

            switch (token) {
                case "(" -> stack.push(token);
                case ")" -> {
                    try {
                        String topElement = stack.pop();
                        while (!topElement.equals("(")) {
                            output.add(topElement);
                            topElement = stack.pop();
                        }
                    }catch(NoSuchElementException e){
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }
                }
                case "*", "/" -> {
                    if (!stack.isEmpty()) {
                        String topToken = stack.peek();
                        while (topToken.equals("*") || topToken.equals("/")) {
                            output.add(stack.pop());
                            if (stack.isEmpty()) {
                                break;
                            }
                            topToken = stack.peek();
                        }
                    }
                    stack.push(token);
                }
                case "+", "-" -> {
                    if (!stack.isEmpty()) {
                        String topToken = stack.peek();
                        while (topToken.equals("*")
                                || topToken.equals("/")
                                || topToken.equals("+")
                                || topToken.equals("-")) {
                            output.add(stack.pop());
                            if (stack.isEmpty()) {
                                break;
                            }
                            topToken = stack.peek();
                        }
                    }
                    stack.push(token);
                }
                default -> output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            String operator = stack.pop();
            if(operator.equals("(")){
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.add(stack.pop());
        }

        return output;
    }

    /**
     * Produces Expression from infix string exp.
     *
     * @param exp infix string expression
     *
     * @return Expression
     */
    public static Expression getExpression(String exp) {
        List<String> rpn = getReversePolish(exp);

        Deque<Expression> result = new ArrayDeque<>();

        for (String s : rpn) {
            if (Character.isDigit(s.charAt(0))) {
                result.push(new Number(Double.parseDouble(s)));
            } else if (s.equals("+")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Sum(leftExpression, rightExpression));
            } else if (s.equals("-")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Sub(leftExpression, rightExpression));
            } else if (s.equals("*")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Mult(leftExpression, rightExpression));
            } else if (s.equals("/")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Div(leftExpression, rightExpression));
            } else {
                result.push(new Variable(s));
            }
        }
        return result.pop();
    }
}