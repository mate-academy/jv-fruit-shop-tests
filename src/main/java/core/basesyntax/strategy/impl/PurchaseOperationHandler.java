package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class PurchaseOperationHandler implements OperationHandler {
    private final Map<String, Integer> storage = FruitStorage.storage;

    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (isTransactionValid(transaction)) {
            storage.put(transaction.getProductName(),
                    storage.get(transaction.getProductName()) - transaction.getQuantity());
        }
    }

    private boolean isTransactionValid(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.PURCHASE) {
            throw new RuntimeException("Error! Transaction has incorrect operation type");
        }
        if (transaction.getProductName().isBlank()) {
            throw new RuntimeException("Error! Can't handle transaction with empty product name");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Error! Transaction can't be with negative value."
                    + " Actual value is '" + transaction.getQuantity() + '\'');
        }
        if (!storage.containsKey(transaction.getProductName())
                || storage.get(transaction.getProductName()) < transaction.getQuantity()) {
            throw new RuntimeException("Can't handle PURCHASE transaction for the '"
                    + transaction.getProductName() + "' with value '" + transaction.getQuantity()
                    + "'. Available quantity is: "
                    + (storage.getOrDefault(transaction.getProductName(), 0)));
        }
        return true;
    }
}
