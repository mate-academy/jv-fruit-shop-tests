package core.basesyntax.strategy;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.model.FruitTransaction;

public interface Strategy {
    void calculation(FruitTransaction fruitTransaction, FruitTransactionDb fruitTransactionDB);
}
