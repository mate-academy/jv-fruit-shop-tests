package core.basesyntax.storage;

import java.util.Map;

public interface FruitStorage {
    void addFruits(String fruit, int quantity);

    void supplyFruits(String fruit, int quantity);

    int getQuantity(String fruit);

    Map<String, Integer> getFruits();
}
