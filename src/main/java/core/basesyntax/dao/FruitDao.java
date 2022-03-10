package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface FruitDao {
    void save(Fruit fruit);

    Integer getValue(String fruit);

    List<Fruit> getAll();

}
