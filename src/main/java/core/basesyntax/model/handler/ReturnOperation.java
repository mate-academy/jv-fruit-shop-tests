package core.basesyntax.model.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int returnQuantity = transaction.getQuantity();
        if (returnQuantity >= 0) {
            Storage.getStorage().merge(transaction.getFruit(), returnQuantity, Integer::sum);
        } else {
            throw new IllegalArgumentException("Illegal return quantity: " + returnQuantity);
        }
    }
}
