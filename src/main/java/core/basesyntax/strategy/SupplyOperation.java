package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Balance quantity cannot be negative: "
                    + transaction.getQuantity());
        }
        int currentQuantity = Storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        Storage.getFruits()
                .put(transaction.getFruit(), transaction.getQuantity() + currentQuantity);
    }
}
