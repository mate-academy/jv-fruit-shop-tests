package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public interface FruitHandler {
    Storage storage = new Storage();
    void apply(FruitTransaction fruitTransaction);

    default void checkTransaction(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null
                || fruitTransaction.getFruit() == null
                || fruitTransaction.getOperation() == null
                || fruitTransaction.getValue() < 0) {
            throw new RuntimeException("FruitTransaction have incorrect data" + fruitTransaction);
        }
    }
}
