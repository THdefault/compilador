package ele.semantic;

import ele.ast.*;

public class TypeChecker {
    private final SymbolTable symbols = new SymbolTable();

    public void visit(Program p) {
        for (Stmt s : p.statements) visitStmt(s);
    }

    private void visitStmt(Stmt s) {
        if (s instanceof VarDecl) {
            VarDecl vd = (VarDecl) s;
            int value = evalExpr(vd.expr);
            symbols.declare(vd.name, value);
        } else if (s instanceof AssignStmt) {
            AssignStmt a = (AssignStmt) s;
            if (!symbols.exists(a.name)) throw new RuntimeException("Variável não declarada: " + a.name);
            evalExpr(a.expr);
        } else if (s instanceof PrintStmt) {
            evalExpr(((PrintStmt) s).expr);
        } else if (s instanceof BlockStmt) {
            for (Stmt st : ((BlockStmt) s).statements) visitStmt(st);
        } else if (s instanceof IfStmt) {
            IfStmt is = (IfStmt) s;
            evalExpr(is.cond);
            visitStmt(is.thenBranch);
            if (is.elseBranch != null) visitStmt(is.elseBranch);
        }
    }

    private int evalExpr(Expr e) {
        if (e instanceof IntLiteral) return ((IntLiteral) e).value;
        if (e instanceof VarRef) {
            String n = ((VarRef) e).name;
            if (!symbols.exists(n)) throw new RuntimeException("Variável não declarada: " + n);
            return symbols.get(n);
        }
        if (e instanceof BinaryExpr) {
            BinaryExpr be = (BinaryExpr) e;
            int l = evalExpr(be.left);
            int r = evalExpr(be.right);
            switch (be.op) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return l / r;
                case "==": return l == r ? 1 : 0;
                case "!=": return l != r ? 1 : 0;
                case ">": return l > r ? 1 : 0;
                case "<": return l < r ? 1 : 0;
            }
        }
        throw new RuntimeException("Expr não suportada: " + e.getClass());
    }
}
