package com.uepb.parser.exceptions;

import java.util.Arrays;

import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class SyntaxError extends RuntimeException{
    
    private String message;

    public SyntaxError(Token recebido, TokenType ...esperado){
        super("Erro Sintático: Foi recebido " + recebido.toString() 
            + " mas era esperado [" + String.join(", ", 
                Arrays.stream(esperado).map(TokenType::toString).toList()
            ) + "]");
    }

    public SyntaxError(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "SyntaxError: " + message;
    }

    
}
