package ele;

import java.nio.file.Files;
import java.nio.file.Paths;
import ele.ast.Program;
import ele.interpreter.Interpreter;
import ele.parser.EleParser;
import ele.lexer.EleLexer;
import ele.semantic.TypeChecker;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar compilador-ele.jar <source.ele>");
            return;
        }
        String file = args[0];
        String src = new String(Files.readAllBytes(Paths.get(file)));

        EleLexer lexer = new EleLexer(src);
        EleParser parser = new EleParser(lexer);
        Program prog = parser.parseProgram();

        // type check
        TypeChecker tc = new TypeChecker();
        tc.visit(prog);

        Interpreter interp = new Interpreter();
        interp.run(prog);
    }
}
