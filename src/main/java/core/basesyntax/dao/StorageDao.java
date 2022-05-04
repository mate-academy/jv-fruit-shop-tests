package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Set;

public interface StorageDao {
    boolean add(Fruit fruit, int quantity);

    Integer get(Fruit fruit);

    Set<Map.Entry<Fruit, Integer>> getAll();

    void clear();
}
