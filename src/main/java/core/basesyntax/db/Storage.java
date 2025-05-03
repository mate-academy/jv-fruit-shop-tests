package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> fruitShopStorage = new HashMap<>();
    private String fruitNameKey;
    private Integer weightValue;

    public Storage(String fruitNameKey, Integer weightValue) {
        this.fruitNameKey = fruitNameKey;
        this.weightValue = weightValue;
    }

    public Map<String, Integer> getFruits() {
        return fruitShopStorage;
    }
}
