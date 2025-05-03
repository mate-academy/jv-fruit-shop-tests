package core.basesyntax.dao;

import java.util.Map;

public interface FruitDao {
    void add(String fruitName, Integer fruitQuantity);

    Integer get(String fruitName);

    void clear();

    Map<String, Integer> getAll();
}
