package ele.lexer;

public class Token {
    public enum Type {
        INT, IF, ELSE, PRINT,
        ID, NUM,
        PLUS, MINUS, TIMES, DIV,
        LPAREN, RPAREN, LBRACE, RBRACE, SEMI, ASSIGN,
        GT, LT, EQ, NE,
        EOF
    }

    public final Type type;
    public final String text;
    public final int intValue;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
        this.intValue = 0;
    }

    public Token(int intValue) {
        this.type = Type.NUM;
        this.text = Integer.toString(intValue);
        this.intValue = intValue;
    }

    public static Token eof() { return new Token(Type.EOF, ""); }

    @Override
    public String toString() { return "Token(" + type + ", '" + text + "')"; }
}
