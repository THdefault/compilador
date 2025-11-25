package ele.interpreter;

import ele.ast.*;
import ele.semantic.SymbolTable;
import java.util.List;

public class Interpreter {
    private final SymbolTable symbols = new SymbolTable();

    public void run(Program p) {
        for (Stmt s : p.statements) execStmt(s);
    }

    private void execStmt(Stmt s) {
        if (s instanceof VarDecl) {
            VarDecl vd = (VarDecl) s;
            int v = evalExpr(vd.expr);
            symbols.declare(vd.name, v);
        } else if (s instanceof PrintStmt) {
            PrintStmt ps = (PrintStmt) s;
            System.out.println(evalExpr(ps.expr));
        } else if (s instanceof BlockStmt) {
            BlockStmt bs = (BlockStmt) s;
            for (Stmt st : bs.statements) execStmt(st);
        } else if (s instanceof AssignStmt) {
            AssignStmt as = (AssignStmt) s;
            int v = evalExpr(as.expr);
            if (!symbols.exists(as.name)) throw new RuntimeException("Variável não declarada: " + as.name);
            symbols.declare(as.name, v);
        } else if (s instanceof IfStmt) {
            IfStmt is = (IfStmt) s;
            int cond = evalExpr(is.cond);
            if (cond != 0) {
                execStmt(is.thenBranch);
            } else {
                execStmt(is.elseBranch);
            }
        } else {
            throw new RuntimeException("Stmt não suportado: " + s.getClass());
        }
    }

    private int evalExpr(Expr e) {
        if (e instanceof IntLiteral) return ((IntLiteral)e).value;
        if (e instanceof BinaryExpr) {
            BinaryExpr be = (BinaryExpr)e;
            int l = evalExpr(be.left);
            int r = evalExpr(be.right);
            switch (be.op) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return l / r; // integer division
                case "==": return l == r ? 1 : 0;
                case "!=": return l != r ? 1 : 0;
                case ">": return l > r ? 1 : 0;
                case "<": return l < r ? 1 : 0;
            }
        }
        if (e instanceof VarRef) {
            String n = ((VarRef)e).name;
            return symbols.get(n);
        }
        throw new RuntimeException("Expr não suportada: " + e.getClass());
    }
}
