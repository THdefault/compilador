package ele.parser;

import ele.lexer.EleLexer;
import ele.lexer.Token;
import ele.lexer.Token.Type;
import ele.ast.*;

import java.util.ArrayList;
import java.util.List;

public class EleParser {
    private final EleLexer lexer;
    private Token cur;

    public EleParser(EleLexer lexer) {
        this.lexer = lexer;
        this.cur = lexer.nextToken();
    }

    private void eat(Type t) {
        if (cur.type != t) throw new RuntimeException("Expected " + t + " but found " + cur.type + " (" + cur.text + ")");
        cur = lexer.nextToken();
    }

    public Program parseProgram() {
        List<Stmt> stmts = new ArrayList<>();
        while (cur.type != Type.EOF) {
            stmts.add(parseStmt());
        }
        return new Program(stmts);
    }

    private Stmt parseStmt() {
        if (cur.type == Type.INT) {
            eat(Type.INT);
            String name = cur.text; eat(Type.ID);
            eat(Type.ASSIGN);
            Expr e = parseExpr();
            eat(Type.SEMI);
            return new VarDecl(name, e);
        }
        if (cur.type == Type.ID) {
            String name = cur.text; eat(Type.ID);
            eat(Type.ASSIGN);
            Expr e = parseExpr();
            eat(Type.SEMI);
            return new AssignStmt(name, e);
        }
        if (cur.type == Type.PRINT) {
            eat(Type.PRINT); eat(Type.LPAREN);
            Expr e = parseExpr(); eat(Type.RPAREN); eat(Type.SEMI);
            return new PrintStmt(e);
        }
        if (cur.type == Type.IF) {
            eat(Type.IF); eat(Type.LPAREN);
            Expr cond = parseExpr(); eat(Type.RPAREN);
            BlockStmt thenB = parseBlock();
            BlockStmt elseB = new BlockStmt(new ArrayList<Stmt>());
            if (cur.type == Type.ELSE) { eat(Type.ELSE); elseB = parseBlock(); }
            return new IfStmt(cond, thenB, elseB);
        }
        if (cur.type == Type.LBRACE) return parseBlock();
        throw new RuntimeException("Unknown statement starting with: " + cur.type + " (" + cur.text + ")");
    }

    private BlockStmt parseBlock() {
        eat(Type.LBRACE);
        List<Stmt> stmts = new ArrayList<>();
        while (cur.type != Type.RBRACE) stmts.add(parseStmt());
        eat(Type.RBRACE);
        return new BlockStmt(stmts);
    }

    private Expr parseExpr() { 
    return parseComparison();
}

private Expr parseComparison() {
    Expr left = parseEquality();
    while (cur.type == Type.GT || cur.type == Type.LT) {
        String op = cur.text; 
        eat(cur.type);
        left = new BinaryExpr(left, op, parseEquality());
    }
    return left;
}

    private Expr parseEquality() {
        Expr left = parseTerm();
        while (cur.type == Type.EQ || cur.type == Type.NE) {
            String op = cur.text; eat(cur.type);
            left = new BinaryExpr(left, op, parseTerm());
        }
        return left;
    }

    private Expr parseTerm() {
        Expr left = parseFactor();
        while (cur.type == Type.PLUS || cur.type == Type.MINUS) {
            String op = cur.text; eat(cur.type);
            left = new BinaryExpr(left, op, parseFactor());
        }
        return left;
    }

    private Expr parseFactor() {
        Expr left = parseUnary();
        while (cur.type == Type.TIMES || cur.type == Type.DIV) {
            String op = cur.text; eat(cur.type);
            left = new BinaryExpr(left, op, parseUnary());
        }
        return left;
    }

    private Expr parseUnary() {
        if (cur.type == Type.NUM) { int v = cur.intValue; eat(Type.NUM); return new IntLiteral(v); }
        if (cur.type == Type.ID) { String n = cur.text; eat(Type.ID); return new VarRef(n); }
        if (cur.type == Type.LPAREN) { eat(Type.LPAREN); Expr e = parseExpr(); eat(Type.RPAREN); return e; }
        throw new RuntimeException("Unexpected token in expression: " + cur.type);
    }
}
