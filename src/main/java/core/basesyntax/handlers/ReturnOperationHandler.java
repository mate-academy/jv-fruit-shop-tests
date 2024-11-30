package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void calculateOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidDataException("Negative quantity");
        } else if (transaction.getFruit() == null) {
            throw new IllegalArgumentException("Invalid fruit type");
        }
        int currentAmount = Storage.STORAGE.get(transaction.getFruit());
        int quantity = transaction.getQuantity();
        int returnResult = currentAmount + quantity;
        Storage.STORAGE.put(transaction.getFruit(), returnResult);
    }
}
