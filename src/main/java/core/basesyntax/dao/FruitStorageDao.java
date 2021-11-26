package core.basesyntax.dao;

import java.util.Map;
import java.util.Set;

public interface FruitStorageDao {
    void add(String fruitName, int quantity);

    void update(String fruitName, int quantity);

    Set<Map.Entry<String, Integer>> getAll();
}
