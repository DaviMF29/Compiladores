package com.uepb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.uepb.lexer.RegexLexer;
import com.uepb.parser.ExpressionParser;
import com.uepb.parser.TokenBuffer;
import com.uepb.parser.ast.ExprNode;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "code_example.uepb";
        if (args.length > 0) {
            filePath = args[0];
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line); // ESSA LINHA É PRA IMPRIMIR code_example.uepb
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return; 
        }


        RegexLexer lexer = new RegexLexer(filePath);
        try (TokenBuffer tokenBuffer = new TokenBuffer(lexer)) {
            ExpressionParser parser = new ExpressionParser(tokenBuffer);
            ExprNode resultado = parser.parse();
            double finalValue = resultado.evaluate();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro de sintaxe: " + e.getMessage());
        }
    }

    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }
}
