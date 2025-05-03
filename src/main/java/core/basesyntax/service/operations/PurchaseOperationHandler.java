package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Integer lastSumm = Storage.fruits.get(fruitTransaction.getFruitName());
        Storage.fruits.put(fruitTransaction.getFruitName(),
                lastSumm - fruitTransaction.getQuantity());
    }
}
