package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void calculateOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidDataException("Negative quantity");
        }
        if (transaction.getFruit() == null || transaction.getOperation() == null) {
            throw new IllegalArgumentException("Invalid transaction or fruit type");
        }
        int currentAmount = Storage.storage.get(transaction.getFruit());
        int quantity = transaction.getQuantity();
        int supplyResult = currentAmount + quantity;
        Storage.storage.put(transaction.getFruit(), supplyResult);
    }
}
