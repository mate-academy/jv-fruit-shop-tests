package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitsDao {
    void save(Fruit operationFruit, int value);

    int get(Fruit operationFruit);

    Map<Fruit, Integer> getAll();
}
