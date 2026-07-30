package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Supply quantity cannot be negative: "
                    + transaction.getQuantity());
        }
        Storage.getFruits().put(transaction.getFruit(), transaction.getQuantity());
    }
}

