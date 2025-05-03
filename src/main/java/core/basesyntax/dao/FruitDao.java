package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Optional;

public interface FruitDao {
    void put(Fruit fruitType, Integer value);

    Optional<Integer> get(Fruit fruitType);

    Map<Fruit, Integer> getAll();
}
