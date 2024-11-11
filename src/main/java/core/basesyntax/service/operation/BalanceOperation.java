package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void updateStorage(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("The quantity " + transaction.getQuantity()
                    + " of " + transaction.getFruit()
                    + " of the operation " + transaction.getOperation()
                    + " cannot be negative.");
        }
        FruitStorage.fruitStorage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
