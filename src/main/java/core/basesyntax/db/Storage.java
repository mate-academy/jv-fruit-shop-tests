package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> quantities = new HashMap<>();

    public static void addQuantity(FruitTransaction fruitTransaction) {
        quantities.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }

    public static int getFruitQuantity(String fruit) {
        return quantities.getOrDefault(fruit, 0);
    }
}
