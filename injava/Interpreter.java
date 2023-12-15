package injava;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static injava.TokenType.*;

public class Interpreter {

    private final Map<String, Integer> variables = new HashMap<>();
    private final List<Token> tokens;
    private int currentToken = 0;
    
    Interpreter(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Map<String, Integer> interpret() {
        while (tokens.get(currentToken).type != EOF) {
            try {
                assignment();
            } catch (RuntimeException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        return variables;
    }


    private void assignment() {
        consume(IDENTIFIER);
        String varName = tokens.get(currentToken-1).lexeme;
        consume(EQUAL);
        int val = expression();
        consume(SEMICOLON);

        variables.put(varName, val);
    }

    private int expression() {

        int lhs = term();

        while (match(PLUS) || match(MINUS)) {
            if (match(PLUS)) {
                advance();
                lhs += term();
            }else if (match(MINUS)) {
                advance();
                lhs -= term();
            }
        }
        return lhs;
    }

    private int term() {

        int lhs = fact();
        while ((match(STAR))) {
            advance();
            lhs *= fact();
        }        
        return lhs;
    }

    private int fact() {

        if (match(LEFT_PARENT)) {
            advance();
            int val = expression();
            consume(RIGHT_PARENT);
            return val;
        }

        if (match(MINUS)) {
            advance();
            return -fact();
        }

        if (match(PLUS)) {
            advance();
            return fact();
        }
        
        if (match(IDENTIFIER) || match(LITERAL)) {
            advance();
            return evaluate();
        }

        throw new RuntimeException("non literal or identifier.");
    }

    private int evaluate() {
        switch (tokens.get((currentToken - 1)).type) {
            case IDENTIFIER:

                if (variables.get(tokens.get(currentToken - 1).lexeme) != null)
                {
                    return variables.get(tokens.get(currentToken - 1).lexeme);
                }
                throw new RuntimeException("Uninitialized variable.");
            case LITERAL:
                return (int)tokens.get(currentToken - 1).literal;
        }
        throw new RuntimeException("How did you reach this?"); // in theory unreachable
    }

    private boolean match(TokenType type) {
        return tokens.get(currentToken).type == type;
    }

    private void consume(TokenType type) {
        if (match(type)) {
            advance();
            return;
        }
        advance();
        throw new RuntimeException("type mismatch.");
    }

    private void advance() {
        currentToken++;
    }
}