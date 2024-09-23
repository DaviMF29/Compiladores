package com.uepb.lexer;

import java.util.regex.Pattern;

public enum TokenPattern {
    WHITESPACE_PATTERN(Pattern.compile("\\s+")),
    NUMBER_PATTERN(Pattern.compile("\\d+(\\.\\d+)?")), // números inteiros ou decimais
    PLUS_PATTERN(Pattern.compile("\\+")),  // operador +
    MINUS_PATTERN(Pattern.compile("\\-")),  // operador -
    MULTIPLY_PATTERN(Pattern.compile("\\*")),  // operador *
    DIVIDE_PATTERN(Pattern.compile("/")),  // operador /
    LPAREN_PATTERN(Pattern.compile("\\(")), // abre parênteses
    RPAREN_PATTERN(Pattern.compile("\\)")), // fecha parênteses
    COMMENT_PATTERN(Pattern.compile("#.*")),
    SEMICOLON_PATTERN(Pattern.compile(";")),
    POWER_PATTERN(Pattern.compile("\\^"));


    private final Pattern pattern;

    TokenPattern(Pattern pattern){
        this.pattern = pattern;
    }

    public Pattern getPattern(){
        return pattern;
    }
}
