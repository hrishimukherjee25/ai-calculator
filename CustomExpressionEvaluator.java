package chatgpt;

import java.util.HashMap;
import java.util.Map;

public class CustomExpressionEvaluator {

    private static Map<String, Double> variables;

    static {
        variables = new HashMap<>();
        // Initialize variables if needed
        variables.put("e", Math.E);
        variables.put("f", 42.0); // Replace with the actual value for 'f'
        variables.put("imm", 3.14); // Replace with the actual value for 'imm'
        variables.put("ce", 7.980215);
        variables.put("ec", -0.00000000000000000000000000000000000000000000000000001);
        variables.put("tdidf", 0.789);
        // Add more variables as needed
    }

    public static double evaluate(String expression) {
        return parseExpression(expression, 0);
    }

    private static double parseExpression(String expression, int pos) {
        return parseAddSubtract(expression, pos);
    }

    private static double parseAddSubtract(String expression, int pos) {
        double result = parseMultiplyDivide(expression, pos);
        while (true) {
            if (eat(expression, "+", pos)) result += parseMultiplyDivide(expression, pos);
            else if (eat(expression, "-", pos)) result -= parseMultiplyDivide(expression, pos);
            else return result;
        }
    }

    private static double parseMultiplyDivide(String expression, int pos) {
        double result = parseFactor(expression, pos);
        while (true) {
            if (eat(expression, "*", pos)) result *= parseFactor(expression, pos);
            else if (eat(expression, "/", pos)) result /= parseFactor(expression, pos);
            else return result;
        }
    }

    private static double parseFactor(String expression, int pos) {
        if (eat(expression, "+", pos)) return parseFactor(expression, pos);
        if (eat(expression, "-", pos)) return -parseFactor(expression, pos);

        double result;

        int startPos = pos;
        if (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '.') {
            while (Character.isDigit(expression.charAt(pos)) || expression.charAt(pos) == '.') {
                pos++;
            }
            result = Double.parseDouble(expression.substring(startPos, pos));
        } else if (Character.isLetter(expression.charAt(pos))) {
            while (Character.isLetter(expression.charAt(pos))) {
                pos++;
            }
            String symbol = expression.substring(startPos, pos);
            if (variables.containsKey(symbol)) {
                result = variables.get(symbol);
            } else {
                throw new IllegalArgumentException("Unknown symbol: " + symbol);
            }
        } else if (expression.charAt(pos) == '(') {
            pos++;
            result = parseExpression(expression, pos);
            if (expression.charAt(pos) != ')') {
                throw new IllegalArgumentException("Expected closing parenthesis at position " + pos);
            }
            pos++;
        } else {
            throw new IllegalArgumentException("Unexpected character: " + expression.charAt(pos));
        }

        return result;
    }

    private static boolean eat(String expression, String s, int pos) {
        while (pos < expression.length() && Character.isWhitespace(expression.charAt(pos))) {
            pos++;
        }
        if (pos + s.length() <= expression.length() && expression.substring(pos, pos + s.length()).equals(s)) {
            pos += s.length();
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // Example expressions
        String expression1 = "e + f - imm * 2";
        String expression2 = "ce + f^-1 * ceta";
        String expression3 = "tdidf / pagerank + no";

        System.out.println(expression1 + " = " + evaluate(expression1));
        System.out.println(expression2 + " = " + evaluate(expression2));
        System.out.println(expression3 + " = " + evaluate(expression3));
    }
}

