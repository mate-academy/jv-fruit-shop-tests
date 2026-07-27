package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        int balance = Storage.getFruits().getOrDefault(
                transaction.getFruit(), 0)
                - transaction.getQuantity();

        if (balance < 0) {
            throw new RuntimeException(
                    "Not enough " + transaction.getFruit());
        }

        Storage.getFruits().put(
                transaction.getFruit(),
                balance
        );
    }
}
