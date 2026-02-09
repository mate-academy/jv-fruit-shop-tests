package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        Storage.getFruits().put(
                transaction.getFruit(),
                Storage.getFruits().getOrDefault(transaction.getFruit(), 0)
                        + transaction.getQuantity()
        );
    }
}
