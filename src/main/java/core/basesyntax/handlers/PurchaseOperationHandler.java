package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void calculateOperation(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new InvalidDataException("Negative quantity");
        }
        if (transaction.getFruit() == null) {
            throw new IllegalArgumentException("Invalid fruit type");
        }
        int currentAmount = Storage.storage.get(transaction.getFruit());
        int quantity = transaction.getQuantity();
        int purchaseResult = currentAmount - quantity;
        if (purchaseResult < 0) {
            throw new InvalidDataException("Not enough fruits in storage");
        }
        Storage.storage.put(transaction.getFruit(), purchaseResult);
    }
}
