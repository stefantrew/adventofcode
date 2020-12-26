package trew.stefan.aoc2020.day18;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum TokenType {
    ADD,
    SUB,
    MUL,
    DIV,
    POW,
    LPAR,
    RPAR,
    VALUE;

    @Override
    public String toString() {
        switch (this.ordinal()) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            case 4:
                return "^";
            case 5:
                return "(";
            case 6:
                return ")";
            case 7:
                return this.name();
            default:
                return "null";
        }
    }


    public int getPrecedence() {
        switch (this) {
            case ADD:
            case SUB:
                return 3;
            case MUL:
            case DIV:
                return 2;
            case POW:
                return 4;
        }

        return 0;
    }

    public static TokenType fromString(String s) {
        switch (s) {
            case "+":
                return TokenType.ADD;
            case "-":
                return TokenType.SUB;
            case "*":
                return TokenType.MUL;
            case "/":
                return TokenType.DIV;
            case "^":
                return TokenType.POW;
            case "(":
                return TokenType.LPAR;
            case ")":
                return TokenType.RPAR;
            default:
                return TokenType.VALUE;
        }
    }

    public boolean isOperator() {
        switch (this) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case POW:
                return true;
        }
        return false;
    }

    public Long doOperation(Long l, Long r) {
        switch (this) {
            case ADD:
                return r + l;
            case SUB:
                return r - l;
            case MUL:
                return r * l;
            case DIV:
                return r / l;

        }
        return 0L;
    }
}

class ScannedToken {
    private String expressionPiece;
    private TokenType type;

    public ScannedToken(String exp, TokenType type) {
        this.expressionPiece = exp;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(Expr:" + expressionPiece + ", Token:" + type + ")";
    }

    public TokenType type() {
        return type;
    }

    public String expression() {
        return expressionPiece;
    }

    public Long getValue() {
        return Long.parseLong(expressionPiece);
    }
}

@Slf4j
public class Day18 implements AOCDay {


    private int day = 18;

    List<ScannedToken> parseString(String expr) {
        expr = expr.replaceAll(" ", "");
        List<ScannedToken> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (char c : expr.toCharArray()) {
            TokenType type = TokenType.fromString(String.valueOf(c));
            if (type == TokenType.VALUE) {
                builder.append(c);
            } else {
                if (builder.length() > 0) {
                    result.add(new ScannedToken(builder.toString(), TokenType.VALUE));
                    builder = new StringBuilder();
                }
                result.add(new ScannedToken(String.valueOf(c), type));
            }
        }
        if (builder.length() > 0) {
            result.add(new ScannedToken(builder.toString(), TokenType.VALUE));
        }
        return result;
    }

    public long evaluatePostfix(List<ScannedToken> list) {
        Stack<Long> values = new Stack<>();
        int current = 0;
        for (ScannedToken token : list) {
            if (!token.type().isOperator()) {
                values.push(token.getValue());
            } else {
                long right = values.pop();
                long left = values.pop();
                long result = token.type().doOperation(left, right);
                values.push(result);
            }
        }

        if (!values.empty()) {
            return values.pop();
        }
        return -100;
    }


    public Stack<ScannedToken> evaluate(List<ScannedToken> list, Boolean usePrecedence) {
        Stack<ScannedToken> values = new Stack<>();
        Stack<ScannedToken> operators = new Stack<>();

        for (ScannedToken token : list) {
//            log.info("Eval {}", token);
            TokenType tokenType = token.type();
            if (tokenType == TokenType.VALUE) {
                values.push(token);
            } else if (tokenType.isOperator()) {
                while (!operators.empty() &&
                        (operators.peek().type().getPrecedence() >= tokenType.getPrecedence() || !usePrecedence)
                        && operators.peek().type() != TokenType.LPAR
                ) {
                    values.push(operators.pop());
                }
                operators.push(token);
            } else if (tokenType == TokenType.LPAR) {
                operators.push(token);
            } else if (tokenType == TokenType.RPAR) {
                while (operators.peek().type() != TokenType.LPAR
                ) {
                    values.push(operators.pop());
                }

                if (operators.peek().type() == TokenType.LPAR) {
                    operators.pop();
                }
            }
        }

        while (!operators.empty()) {
            values.push(operators.pop());
        }

//        for (ScannedToken token : values) {
//            log.info("VALUES {}", token);
//        }
//        log.info("Done");
        return values;
    }

    @Override
    public String runPart1() {
        return String.valueOf(run(false));
    }

    @Override
    public String runPart2() {
        return String.valueOf(run(true));
    }

    public Long run(boolean usePrecedence) {
        List<String> lines = InputReader2020.readStrings(day, "");

        Long total = 0L;
        for (String line : lines) {
            List<ScannedToken> tokens = parseString(line);

            List<ScannedToken> output = evaluate(tokens, usePrecedence);
            long result = evaluatePostfix(output);
            total += result;
        }

        return total;
    }
}
//1735429
