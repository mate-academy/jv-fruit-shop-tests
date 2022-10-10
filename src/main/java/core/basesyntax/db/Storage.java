package core.basesyntax.db;

import java.util.TreeMap;

public class Storage {
    private static final TreeMap<String, Integer> fruits = new TreeMap<>();

    public static TreeMap<String, Integer> getFruits() {
        return fruits;
    }
}
