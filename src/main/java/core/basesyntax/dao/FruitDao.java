package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface FruitDao {
    boolean add(Fruit fruit);

    Fruit get(String fruitName);

    boolean update(Fruit fruit);

    List<Fruit> getAll();
}
