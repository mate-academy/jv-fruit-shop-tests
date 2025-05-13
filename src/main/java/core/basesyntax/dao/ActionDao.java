package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface ActionDao {
    void addFruitTransaction(FruitTransaction fruitTransaction);

    List<FruitTransaction> getListTransactions();
}
