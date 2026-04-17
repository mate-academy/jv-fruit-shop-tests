package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class BalanceOperation implements OperationHandler {

    @Override
    public void handleOperation(FruitTransaction transaction, Map<String, Integer> storage) {
        if (transaction == null) {
            throw new RuntimeException("Transaction is null");
        }

        if (transaction.getOperation() == null) {
            throw new RuntimeException("Operation is null");
        }

        if (transaction.getFruit() == null || transaction.getFruit().isEmpty()) {
            throw new RuntimeException("Fruit is null or empty" + transaction.getFruit());
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity is negative" + transaction.getQuantity());
        }

        if (storage == null) {
            throw new RuntimeException("Storage is null");
        }

        storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
