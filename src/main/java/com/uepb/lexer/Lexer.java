package com.uepb.lexer;

import java.io.IOException;
import com.uepb.token.Token;

public interface Lexer {
    Token readNextToken() throws IOException;
    void close() throws IOException;
}
