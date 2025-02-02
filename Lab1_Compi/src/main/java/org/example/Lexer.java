package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer implements ILexer {
    private final Pattern pattern = Pattern.compile(
            "\\b(bin|oct|hex)\\b|" +                // Palabras clave
                    "[Bb][01]+|" +                          // Números binarios
                    "[Oo][0-7]+|" +                         // Números octales
                    "[Hh][0-9A-Fa-f]+|" +                   // Números hexadecimales
                    "[a-zA-Z_][a-zA-Z0-9_]*|" +             // Identificadores
                    "[+\\-*/()]|" +                         // Operadores
                    ";"
    );

    @Override
    public List<TokenSymbol> getTokensFromString(String expresion) {
        System.out.println("\n[LEXER] Procesando entrada: " + expresion);
        List<TokenSymbol> tokens = new ArrayList<>();
        Matcher matcher = pattern.matcher(expresion);

        while (matcher.find()) {
            String match = matcher.group();
            TokenSymbol token = null;

            if (match.matches("\\b(bin|oct|hex)\\b")) {
                token = new TokenSymbol("TYPE", match);
            }
            else if (match.matches("[Bb][01]+")) {
                token = new TokenSymbol("NUMBIN", match);
            }
            else if (match.matches("[Oo][0-7]+")) {
                token = new TokenSymbol("NUMOCT", match);
            }
            else if (match.matches("[Hh][0-9A-Fa-f]+")) {
                token = new TokenSymbol("NUMHEX", match);
            }
            else if (match.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                token = new TokenSymbol("ID", match);
            }
            else if (match.equals("+")) token = new TokenSymbol("PLUS", match);
            else if (match.equals("-")) token = new TokenSymbol("MINUS", match);
            else if (match.equals("*")) token = new TokenSymbol("MULT", match);
            else if (match.equals("/")) token = new TokenSymbol("DIV", match);
            else if (match.equals("(")) token = new TokenSymbol("LPAR", match);
            else if (match.equals(")")) token = new TokenSymbol("RPAR", match);
            else if (match.equals(";")) token = new TokenSymbol("SEMI", match);

            if (token != null) {
                System.out.println("[LEXER] Token reconocido: " + token.getType() + " -> " + token.getValue());
                tokens.add(token);
            }
        }

        tokens.add(new TokenSymbol("$", "$"));
        return tokens;
    }
}