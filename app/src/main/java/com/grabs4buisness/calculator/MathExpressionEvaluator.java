package com.grabs4buisness.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class MathExpressionEvaluator {

    public BigDecimal evaluateExpression(String expression) {
        try {
            return evaluateExpressionRecursively(expression);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal evaluateExpressionRecursively(String expression) {
        expression = expression.replaceAll("\\s", ""); // Remove spaces
        // Check if the expression contains "/0"
        if (expression.contains("/0")) {
            return new BigDecimal("Can't divide by zero");
        }

        Stack<BigDecimal> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        boolean isNegative = false; // Flag to track if the expression starts with a negative sign

        if (expression.startsWith("-")) {
            isNegative = true;
            expression = expression.substring(1); // Remove the negative sign
        }

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                int startIndex = i;
                int endIndex = i;

                while (endIndex < expression.length() && (Character.isDigit(expression.charAt(endIndex)) || expression.charAt(endIndex) == '.')) {
                    endIndex++;
                }
                String numberStr = expression.substring(startIndex, endIndex);
                BigDecimal number = new BigDecimal(numberStr);

                // If it's a negative number, negate it
                if (isNegative) {
                    number = number.negate();
                    isNegative = false; // Reset the negative flag
                }

                operands.push(number);
                i = endIndex;
            }
            else if (c == '%') {
                // Handle '%' as a special case by dividing the last operand by 100
                BigDecimal lastOperand = operands.pop();
                BigDecimal result = lastOperand.divide(BigDecimal.valueOf(100), 7, RoundingMode.HALF_UP);
                operands.push(result);
                i++;
            }
            else if (isOperator(c)) {
                while (!operators.isEmpty() && hasHigherOrEqualPrecedence(operators.peek(), c)) {
                    BigDecimal rightOperand = operands.pop();
                    BigDecimal leftOperand = operands.pop();
                    char operator = operators.pop();
                    operands.push(performOperation(leftOperand, rightOperand, operator));
                }
                operators.push(c);
                i++;
            } else {
                i++;
            }
        }

        while (!operators.isEmpty()) {
            BigDecimal rightOperand = operands.pop();
            BigDecimal leftOperand = operands.pop();
            char operator = operators.pop();
            operands.push(performOperation(leftOperand, rightOperand, operator));
        }

        return operands.pop();
    }

    private boolean hasHigherOrEqualPrecedence(char op1, char op2) {
        if ((op1 == '+' || op1 == '-') && (op2 == '*' || op2 == '/' || op2 == '^' || op2 == '%')) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '%';
    }

    private BigDecimal performOperation(BigDecimal left, BigDecimal right, char operator) {
        if (operator == '%') {
            // Check if '%' is entered after a number, then divide that number by 100
            return left.divide(BigDecimal.valueOf(100), 7, RoundingMode.HALF_UP);
        } else {
            switch (operator) {
                case '+':
                    return left.add(right);
                case '-':
                    return left.subtract(right);
                case '*':
                    return left.multiply(right);
                case '/':
                    return left.divide(right, 7, RoundingMode.HALF_UP);
                case '^':
                    return left.pow(right.intValue());
                default:
                    return BigDecimal.ZERO;
            }
        }
    }

}
