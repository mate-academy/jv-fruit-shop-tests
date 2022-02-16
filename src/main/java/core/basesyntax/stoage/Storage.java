package core.basesyntax.stoage;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String,Integer> storageOfFruit = new HashMap<String,Integer>();

    public Storage() {
        storageOfFruit = new HashMap<String,Integer>();
    }

    public static Map<String,Integer> storage() {
        return storageOfFruit;
    }
}
