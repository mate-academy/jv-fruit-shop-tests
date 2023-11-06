package core.basesyntax.transactions.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactions.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private static final String INVALID_TRANSACTION = "Invalid transaction: ";
    private static final String NEGATIVE_QUANTITY = "Negative quantity: ";

    @Override
    public boolean processTransaction(FruitTransaction transaction) {
        if (transaction == null || transaction.getFruit() == null) {
            throw new IllegalArgumentException(INVALID_TRANSACTION + transaction);
        }

        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int balance = Storage.STORAGE_VALUE.getOrDefault(fruit, 0);

        if (quantity < 0) {
            throw new IllegalArgumentException(NEGATIVE_QUANTITY + transaction);
        }

        int updatedBalance = balance + quantity;
        Storage.STORAGE_VALUE.put(fruit, updatedBalance);

        return true;
    }
}
