package core.basesyntax.db;

import java.util.Map.Entry;
import java.util.Set;

public interface FruitTransactionDb {
    Set<Entry<String, Integer>> getAllFruitsTransaction();

    int getQuantity(String fruit);

    void addQuantity(String fruit, int quantity);

    void subtractQuantity(String fruit, int quantity);
}
