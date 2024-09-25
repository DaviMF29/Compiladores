package com.uepb.parser.ast;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Object> table = new HashMap<>();

    public void put(String name, Object value) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome da variável não pode ser nulo ou vazio.");
        }
        table.put(name, value);
    }

    public Object get(String name) {
        return table.get(name);
    }
}
