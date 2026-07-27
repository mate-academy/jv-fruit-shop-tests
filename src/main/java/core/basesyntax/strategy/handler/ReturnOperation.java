package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        Storage.getFruits().put(
                transaction.getFruit(),
                Storage.getFruits().getOrDefault(
                        transaction.getFruit(), 0)
                        + transaction.getQuantity()
        );
    }
}
