package com.uepb.parser;

import java.io.IOException;
import com.uepb.lexer.Lexer;
import com.uepb.parser.exceptions.SyntaxError;
import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class Parser {

    private final TokenBuffer tokens;

    public Parser(Lexer lexer) throws IOException {
        this.tokens = new TokenBuffer(lexer);
    }

    public double parseExpression() throws IOException, SyntaxError {
        return parseTerm();
    }

    // term -> factor ( ( '+' | '-' ) factor )*
    private double parseTerm() throws IOException, SyntaxError {
        double result = parseFactor();

        while (true) {
            Token token = tokens.lookAhead(1);

            if (token.type() == TokenType.PLUS) {
                tokens.match(TokenType.PLUS);
                result += parseFactor();
            } else if (token.type() == TokenType.MINUS) {
                tokens.match(TokenType.MINUS);
                result -= parseFactor();
            } else {
                break;
            }
        }

        return result;
    }

    private double parseFactor() throws IOException, SyntaxError {
        Token token = tokens.lookAhead(1);

        if (token.type() == TokenType.NUMBER) {
            tokens.match(TokenType.NUMBER);
            return Double.parseDouble(token.lexema());
        } else if (token.type() == TokenType.LPAREN) {
            tokens.match(TokenType.LPAREN);
            double result = parseExpression();
            tokens.match(TokenType.RPAREN);
            return result;
        }

        throw new SyntaxError("Unexpected token: " + token);
    }
}
