package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void calculateOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidDataException("Negative quantity");
        }
        if (transaction.getFruit() == null || transaction.getOperation() == null) {
            throw new IllegalArgumentException("Invalid transaction or fruit type");
        }
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
