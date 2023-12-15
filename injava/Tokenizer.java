package injava;

import java.util.List;
import java.util.ArrayList;

import static injava.TokenType.*;

public class Tokenizer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    
    Tokenizer(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        
        tokens.add(new Token(EOF, "", null)); // final tooken is eof
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PARENT); break;
            case ')': addToken(RIGHT_PARENT); break;
            case '+': addToken(PLUS); break;
            case '-': addToken(MINUS); break;
            case '*': addToken(STAR); break;
            case '=': addToken(EQUAL); break;
            case ';': addToken(SEMICOLON); break;
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                // ignore whitespace
                break;
            default:
                if (isDigit(c)) {
                    number(c);
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    tokens.add(new Token(ERROR, "", null));
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek()))
            advance();
        
        addToken(IDENTIFIER);
    }

    private void number(char c) {
        if (c == '0') {
            if (isDigit(peek())) {
                while (isDigit(peek())) advance();
                tokens.add(new Token(ERROR, "", null));
                return;
            } else {
                addToken(LITERAL, 0);
                return;
            }
        }
        while (isDigit(peek())) {
                advance();
        }

        addToken(LITERAL, Integer.parseInt(source.substring(start, current)));
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token (type, text, literal));
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }
}   



    

