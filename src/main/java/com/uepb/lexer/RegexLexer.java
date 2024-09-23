package com.uepb.lexer;

import java.io.IOException;

import com.uepb.token.Token;
import com.uepb.token.TokenType;

public class RegexLexer implements Lexer {

    private LineBuffer buffer;

    public RegexLexer(String filePath) throws IOException {
        this.buffer = new LineBuffer(filePath);
    }

    @Override
    public Token readNextToken() throws IOException {
        String tokenValue;

        while (buffer.getReadedLine() != null) {
            if (buffer.getCol() >= buffer.getReadedLine().length()) {
                buffer.nextLine();
                continue;
            }

            if ((tokenValue = buffer.prefixMatches(TokenPattern.WHITESPACE_PATTERN)) != null)
                continue;

            TokenType type = null;

            if ((tokenValue = buffer.prefixMatches(TokenPattern.NUMBER_PATTERN)) != null) {
                type = TokenType.NUMBER;
            }
            else if ((tokenValue = buffer.prefixMatches(TokenPattern.COMMENT_PATTERN)) != null) {
                continue;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.PLUS_PATTERN)) != null) {
                type = TokenType.PLUS;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.MINUS_PATTERN)) != null) {
                type = TokenType.MINUS;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.MULTIPLY_PATTERN)) != null) {
                type = TokenType.MULTIPLY;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.DIVIDE_PATTERN)) != null) {
                type = TokenType.DIVIDE;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.LPAREN_PATTERN)) != null) {
                type = TokenType.LPAREN;
            } else if ((tokenValue = buffer.prefixMatches(TokenPattern.RPAREN_PATTERN)) != null) {
                type = TokenType.RPAREN;
            }

            if (type != null) {
                return new Token(type, tokenValue);
            }

            throw new IOException("Unexpected character: " + buffer.currentChar());
        }

        return new Token(TokenType.EOF, null);
    }

    @Override
    public void close() throws IOException {
        buffer.close();
    }
}
