package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.excteption.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void calculateOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidDataException("Negative quantity");
        } else if (transaction.getFruit() == null || transaction.getOperation() == null) {
            throw new NullPointerException("Invalid transaction or fruit type");
        }
        int currentAmount = Storage.STORAGE.get(transaction.getFruit());
        int quantity = transaction.getQuantity();
        int supplyResult = currentAmount + quantity;
        Storage.STORAGE.put(transaction.getFruit(), supplyResult);
    }
}
