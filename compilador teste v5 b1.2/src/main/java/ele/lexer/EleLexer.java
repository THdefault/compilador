package ele.lexer;

public class EleLexer {
    private final String input;
    private int pos = 0;
    private final int length;

    public EleLexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    private char peek() { return pos < length ? input.charAt(pos) : '\0'; }
    private char next() { return pos < length ? input.charAt(pos++) : '\0'; }

    private void skipWhitespace() {
        while (Character.isWhitespace(peek())) pos++;
    }

    public Token nextToken() {
        skipWhitespace();
        char c = peek();
        if (c == '\0') return Token.eof();

        // identifiers/keywords
        if (Character.isLetter(c) || c == '_') {
            StringBuilder sb = new StringBuilder();
            while (Character.isLetterOrDigit(peek()) || peek() == '_') sb.append(next());
            String s = sb.toString();
            switch (s) {
                case "int": return new Token(Token.Type.INT, s);
                case "if": return new Token(Token.Type.IF, s);
                case "else": return new Token(Token.Type.ELSE, s);
                case "print": return new Token(Token.Type.PRINT, s);
                default: return new Token(Token.Type.ID, s);
            }
        }

        // numbers
        if (Character.isDigit(c)) {
            int v = 0;
            while (Character.isDigit(peek())) {
                v = v * 10 + (next() - '0');
            }
            return new Token(v);
        }

        // two-char operators and single-char
        if (c == '=') {
            next();
            if (peek() == '=') { next(); return new Token(Token.Type.EQ, "=="); }
            return new Token(Token.Type.ASSIGN, "=");
        }
        if (c == '!') {
            next();
            if (peek() == '=') { next(); return new Token(Token.Type.NE, "!="); }
            // unsupported '!' alone
        }
        if (c == '>') { next(); return new Token(Token.Type.GT, ">"); }
        if (c == '<') { next(); return new Token(Token.Type.LT, "<"); }

        switch (c) {
            case '+': next(); return new Token(Token.Type.PLUS, "+");
            case '-': next(); return new Token(Token.Type.MINUS, "-");
            case '*': next(); return new Token(Token.Type.TIMES, "*");
            case '/': next(); return new Token(Token.Type.DIV, "/");
            case '(' : next(); return new Token(Token.Type.LPAREN, "(");
            case ')' : next(); return new Token(Token.Type.RPAREN, ")");
            case '{' : next(); return new Token(Token.Type.LBRACE, "{");
            case '}' : next(); return new Token(Token.Type.RBRACE, "}");
            case ';' : next(); return new Token(Token.Type.SEMI, ";");
            case ',' : next(); return new Token(Token.Type.SEMI, ",");
        }

        // unknown -> skip
        next();
        return nextToken();
    }
}
