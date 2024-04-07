package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationStrategy {

    @Override
    public void processTransaction(FruitTransaction transaction, Storage storage) {
        int currentQuantity = storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        int resultQuantity = currentQuantity + transaction.getQuantity();
        storage.setFruits(transaction.getFruit(), resultQuantity);
    }
}
