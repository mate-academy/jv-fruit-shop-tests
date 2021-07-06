package core.basesyntax.fruitshop.db;

import core.basesyntax.fruitshop.model.Fruit;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FruitStorage {
    private static Map<Fruit, BigDecimal> fruitMap = new HashMap<>();

    private FruitStorage() {
    }

    public static Map<Fruit, BigDecimal> getFruitMap() {
        return fruitMap;
    }
}
