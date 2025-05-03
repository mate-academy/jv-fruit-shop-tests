package core.basesyntax.dao;

import java.util.Map;

public interface FruitDao {
    Map<String, Integer> getData();

    Integer getFruitQuantity(String fruit);

    boolean update(String fruit, Integer quantity);
}
