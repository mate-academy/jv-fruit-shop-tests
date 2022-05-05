package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitsDao {
    void addProduct(Fruit fruit, Integer count);

    Integer getValue(Fruit fruit);

    Map<Fruit, Integer> getAll();
}
