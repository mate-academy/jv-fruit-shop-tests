package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class StorageImpl implements Storage {
    private static final Map<String, Integer> balance = new HashMap<>();

    public static Map<String, Integer> getStorage() {
        return balance;
    }
}
