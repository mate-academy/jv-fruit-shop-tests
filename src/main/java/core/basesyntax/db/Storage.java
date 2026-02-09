package core.basesyntax.db;

public class Storage {
    private static final java.util.Map<String, Integer> storage = new java.util.HashMap<>();

    public static void clear() {
        storage.clear();
    }

    public static java.util.Map<String, Integer> getFruits() {
        return storage;
    }
}
