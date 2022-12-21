package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitStorageDao {
    boolean add(Fruit fruit, Integer amount);

    boolean update(Fruit fruit, Integer amount);

    Integer getAmountByFruit(Fruit fruit);

    Map<Fruit, Integer> getAll();
}
