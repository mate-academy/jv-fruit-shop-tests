package core.basesyntax.dao;

import core.basesyntax.shop.item.Fruit;
import java.util.List;

public interface FruitDao {
    void add(Fruit fruit);

    Fruit get(Fruit fruit);

    void update(Fruit fruit);

    List<Fruit> getAll();
}
