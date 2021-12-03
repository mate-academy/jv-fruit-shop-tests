package core.basesyntax.dao;

import core.basesyntax.model.Fruit;

public interface InputDao {
    void add(Fruit fruit);

    Fruit getFruit(String fruitName);
}
