package ele;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import ele.Main;

public class CompilerTests {
    @Test
    public void sampleRuns() throws Exception {
        // This test just runs the main and ensures no exception is thrown.
        Main.main(new String[] {"sample.ele"});
    }
}
