package core.basesyntax.handlers;

import core.basesyntax.storage.Storage;
import core.basesyntax.transaction.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity is invalid: ";

    @Override
    public void handleOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException(INVALID_QUANTITY_MESSAGE + fruitTransaction.getQuantity());
        }
        Storage.getFruitBalance().put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
