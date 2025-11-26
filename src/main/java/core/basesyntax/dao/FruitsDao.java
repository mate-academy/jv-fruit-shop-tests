package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitsDao {
    void add(FruitTransaction fruits);

    FruitTransaction get(FruitTransaction fruits);

    List<FruitTransaction> getAll();

    void set(FruitTransaction fruits);
}
