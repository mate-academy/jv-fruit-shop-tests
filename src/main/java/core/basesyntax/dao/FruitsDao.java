package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Set;

public interface FruitsDao {
    void add(Fruit fruit, Integer amount);

    Integer getAmount(Fruit fruit);

    Fruit getAmountByType(String type);

    void update(Fruit fruit, Integer amount);

    Set<Map.Entry<Fruit, Integer>> getAll();
}
