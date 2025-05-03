package core.basesyntax.db;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public interface DataBase {
    void addFruitTransaction(FruitTransaction fruitTransaction);

    Map<String, Integer> getFruitTransactions();
}
