package core.basesyntax.operationhandlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransactionImpl;

public class ReturnOperationHandler implements OperationHandler {
    public void apply(FruitTransactionImpl transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        Storage.addFruit(transaction.getFruit(), transaction.getQuantity());
    }
}
