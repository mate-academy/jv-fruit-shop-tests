package core.basesyntax.basesyntax.dao;

import core.basesyntax.basesyntax.model.Fruit;
import java.util.Optional;

public interface FruitDao {
    void add(Fruit fruit);

    Optional<Fruit> getFruitIfPresent(String fruitName);
}
