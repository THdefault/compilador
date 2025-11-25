package ele.ast;

public class VarDecl implements Stmt {
    public final String name;
    public final Expr expr;
    public VarDecl(String name, Expr expr) { this.name = name; this.expr = expr; }
}
