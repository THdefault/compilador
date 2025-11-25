package ele.ast;

public class IfStmt implements Stmt {
    public final Expr cond;
    public final BlockStmt thenBranch;
    public final BlockStmt elseBranch;
    public IfStmt(Expr cond, BlockStmt thenBranch, BlockStmt elseBranch) { this.cond = cond; this.thenBranch = thenBranch; this.elseBranch = elseBranch; }
}
