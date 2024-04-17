package core.basesyntax.dao;

import java.util.Map;
import java.util.Set;

public interface FruitTransactionDao {
    void add(String name, int quantity);

    void update(String name, int quantity);

    int getByName(String name);

    Set<Map.Entry<String, Integer>> getFull();
}
