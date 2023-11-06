package core.basesyntax.transactions.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactions.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private static final String NULL_TRANSACTION = "Transaction is null: ";
    private static final String NEGATIVE_OR_INCORRECT_BALANCE =
            "Invalid or negative balance transaction: ";

    @Override
    public boolean processTransaction(FruitTransaction transaction) {
        if (transaction == null || transaction.getFruit() == null) {
            throw new IllegalArgumentException(NULL_TRANSACTION + transaction);
        }
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        Integer balance = Storage.STORAGE_VALUE.get(transaction.getFruit());
        if (balance < quantity) {
            throw new IllegalArgumentException(NEGATIVE_OR_INCORRECT_BALANCE + transaction);

        } else {
            Storage.STORAGE_VALUE.put(fruit, balance - quantity);
        }
        return true;
    }
}
