package core.basesyntax.db;

import java.util.HashMap;

public final class Storage {
    private static final HashMap<String, Integer> fruitStorage =
            new HashMap<>();

    public static HashMap<String, Integer> getFruitStorage() {
        return fruitStorage;
    }
}
