package basesyntax.service.handler;

import basesyntax.model.FruitTransaction;
import basesyntax.storage.Storage;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int updatedQuantity = Storage.get(fruit) + transaction.getQuantity();
        Storage.put(fruit, updatedQuantity);
    }
}
