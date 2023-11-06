package core.basesyntax.transactions.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactions.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private static final String NULL_TRANSACTION = "Transaction is null: ";
    private static final String NEGATIVE_QUANTITY = "Invalid transaction: ";
    private static final String NOT_FOUND = "Fruit not found in storage: ";

    @Override
    public boolean processTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException(NULL_TRANSACTION + transaction);
        }

        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        if (quantity < 0) {
            throw new IllegalArgumentException(NEGATIVE_QUANTITY + transaction);
        }

        Integer balance = Storage.STORAGE_VALUE.get(fruit);
        if (balance == null) {
            throw new IllegalArgumentException(NOT_FOUND + balance);
        }

        Storage.STORAGE_VALUE.put(fruit, balance + quantity);
        return true;
    }
}
