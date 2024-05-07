package core.basesyntax.testclasses;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;

public class StorageForTest implements Storage {
    private static final Map<String, Integer> testStorage = new HashMap<>();

    public static Map<String, Integer> getTestStorage() {
        return testStorage;
    }
}
