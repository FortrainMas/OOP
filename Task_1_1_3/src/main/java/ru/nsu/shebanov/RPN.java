package ru.nsu.shebanov;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Class which uses reverse polish notation to produce original expression.
 */
public class RPN {
    /**
     * Gets the first token in the string from needed position.
     * token is number, variable name or operation sign
     *
     * @param exp infix expression
     * @param pos position on which start to find tokens
     *
     * @return first found token
     */
    private static String getNextToken(String exp, int pos) {
        char firstSymbol = exp.charAt(pos);
        if (firstSymbol == '+'
                || firstSymbol == '-'
                || firstSymbol == '*'
                || firstSymbol == '/'
                || firstSymbol == '('
                || firstSymbol == ')') {
            return ""+firstSymbol;
        }
        else if(Character.isDigit(firstSymbol)){
            StringBuilder result = new StringBuilder();
            do{
                result.append(exp.charAt(pos));
                pos += 1;
            } while ( pos < exp.length() && Character.isDigit(exp.charAt(pos)));
            return result.toString();
        } else {
            StringBuilder result = new StringBuilder();
            do {
                result.append(exp.charAt(pos));
                pos += 1;
            } while (pos < exp.length()
                    && !Character.isDigit(exp.charAt(pos))
                    && (exp.charAt(pos) != '+'
                            && exp.charAt(pos) != '-'
                            && exp.charAt(pos) != '*'
                            && exp.charAt(pos) != '/'
                            && exp.charAt(pos) != '('
                            && exp.charAt(pos) != ')'));
            return result.toString();
        }
    }

    public static ArrayList<String> getRPN(String exp) {
        ArrayList<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        int position = 0;
        while(position != exp.length()){
            String token = getNextToken(exp, position);
            position += token.length();

            if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                String topElement = stack.pop();
                while (!topElement.equals("(")) {
                    output.add(topElement);
                    topElement = stack.pop();
                }
            } else if (token.equals("*") || token.equals("/")){
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
            } else if(token.equals("+") || token.equals("-")){
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
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()){
            output.add(stack.pop());
        }

        return output;
    }

    public static Expression getExpression(String exp){
        ArrayList<String> rpn = getRPN(exp);

        Stack<Expression> result = new Stack<>();

        for(int i = 0; i < rpn.size(); i++){
            if (Character.isDigit(rpn.get(i).charAt(0))) {
                result.push(new Number(Double.parseDouble(rpn.get(i))));
            } else if (rpn.get(i).equals("+")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Sum(leftExpression, rightExpression));
            } else if(rpn.get(i).equals("-")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Sub(leftExpression, rightExpression));
            } else if (rpn.get(i).equals("*")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Mult(leftExpression, rightExpression));
            } else if(rpn.get(i).equals("/")) {
                Expression rightExpression = result.pop();
                Expression leftExpression = result.pop();
                result.push(new Div(leftExpression, rightExpression));
            } else {
                result.push(new Variable(rpn.get(i)));
            }
        }
        return result.pop();
    }
}
