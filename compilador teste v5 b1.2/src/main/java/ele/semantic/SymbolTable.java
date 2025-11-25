package ele.semantic;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Integer> vars = new HashMap<>();

    public void declare(String name, int value) {
        vars.put(name, value);
    }

    public boolean exists(String name) { return vars.containsKey(name); }

    public int get(String name) { return vars.get(name); }
}
