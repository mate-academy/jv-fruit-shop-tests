package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.Map;

public class Storage {
    private final Map<Fruit, Integer> fruits;

    public Storage(Map<Fruit, Integer> fruits) {
        this.fruits = fruits;
    }

    public Map<Fruit, Integer> getFruits() {
        return fruits;
    }
}
