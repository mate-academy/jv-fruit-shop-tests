package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitTransactionDb;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Strategy;

public class PurchaseStrategy implements Strategy {
    @Override
    public void calculation(FruitTransaction fruitTransaction,
                            FruitTransactionDb fruitTransactionDB) {
        fruitTransactionDB.subtractQuantity(fruitTransaction.getFruit(),
                fruitTransaction.getQuantity());
    }
}
