package com.uepb.parser;

import java.io.IOException;

import com.uepb.parser.exceptions.SyntaxError;
import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class ExpressionParser {

    private final TokenBuffer tokenBuffer;

    public ExpressionParser(TokenBuffer tokenBuffer) {
        this.tokenBuffer = tokenBuffer;
    }

    public void parse() throws IOException, SyntaxError {
        expression();
        tokenBuffer.match(TokenType.EOF); 
    }

    
    private void expression() throws IOException, SyntaxError {
        term(); 
        while (isAddOp(tokenBuffer.lookAhead(1))) { 
            tokenBuffer.match(tokenBuffer.lookAhead(1).type());
            term(); 
        }
    }

    private void term() throws IOException, SyntaxError {
        factor(); 
        while (isMulOp(tokenBuffer.lookAhead(1))) {
            tokenBuffer.match(tokenBuffer.lookAhead(1).type());
            factor();  
        }
    }

    private void factor() throws IOException, SyntaxError {
        Token lookahead = tokenBuffer.lookAhead(1);

        if (lookahead.type() == TokenType.NUMBER) {
            tokenBuffer.match(TokenType.NUMBER); 
        } else if (lookahead.type() == TokenType.LPAREN) {
            tokenBuffer.match(TokenType.LPAREN);  
            expression();
            tokenBuffer.match(TokenType.RPAREN);
        } else {
            throw new SyntaxError(lookahead);
        }
    }

    private boolean isAddOp(Token token) {
        return token.type() == TokenType.PLUS || token.type() == TokenType.MINUS;
    }

    private boolean isMulOp(Token token) {
        return token.type() == TokenType.MULTIPLY || token.type() == TokenType.DIVIDE;
    }
}
