package core.basesyntax.dao;

import core.basesyntax.model.Fruit;

public interface StorageDao {
    Fruit getFruit(Fruit fruit);

    void changeQuantityOfFruit(Fruit fruit);

    void addFruit(Fruit fruit);
}
