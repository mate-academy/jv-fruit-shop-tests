package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> STORAGE = new HashMap<>();

    public static Map<String, Integer> getStorage() {
        return new HashMap<>(STORAGE);
    }
}
