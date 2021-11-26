package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Optional;

public interface FruitDao {
    void add(Fruit fruit, int quality);

    void addAll(Map<Fruit,Integer> fruits);

    Optional<Integer> getQuantity(Fruit fruit);

    Map<Fruit,Integer> getAll();
}
