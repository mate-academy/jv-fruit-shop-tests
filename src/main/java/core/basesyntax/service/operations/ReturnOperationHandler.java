package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Storage.fruits.put(fruitTransaction.getFruitName(),
                Storage.fruits.get(fruitTransaction.getFruitName())
                + fruitTransaction.getQuantity());
    }
}
