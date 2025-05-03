package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitDao {
    void add(String typeOfFruit, Fruit fruit);

    Fruit get(String keyFruit);

    Map<String,Fruit> getAll();
}
