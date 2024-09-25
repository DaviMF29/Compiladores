package com.uepb.parser;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedList;

import com.uepb.lexer.Lexer;
import com.uepb.parser.exceptions.SyntaxError;
import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class TokenBuffer implements Closeable {
    private final int SIZE;
    private boolean reachedEndOfFile;
    private final LinkedList<Token> buffer;
    private final Lexer lexer;
    public TokenBuffer(Lexer lexer) throws IOException {
        SIZE = 10;
        buffer = new LinkedList<>();
        this.lexer = lexer;
        reachedEndOfFile = false;
        confirmToken();
    }

    private void confirmToken() throws IOException {
        while (buffer.size() < SIZE && !reachedEndOfFile) {
            Token token = lexer.readNextToken();
            if (token.type() == TokenType.EOF) {
                reachedEndOfFile = true;
            }
            buffer.add(token);
        }
    }

    public Token lookAhead(int k) {
        if (buffer.isEmpty()) 
            return null;

        k = k - 1 < 0 ? 0 : k - 1;
        return k >= buffer.size() ? buffer.getLast() : buffer.get(k);
    }

    public void match(TokenType expected) throws SyntaxError, IOException {
        Token current = lookAhead(1);
        if (current == null || current.type() != expected) {
            throw new SyntaxError("Esperado: " + expected + ", mas encontrado: " + (current != null ? current.type() : "EOF"));
        }

        buffer.remove(0); 
        confirmToken();
    }

    @Override
    public void close() throws IOException {
        if(lexer != null) lexer.close();
    }
    

}
