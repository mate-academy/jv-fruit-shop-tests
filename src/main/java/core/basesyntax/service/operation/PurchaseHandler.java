package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.NegativeQuantityException;
import core.basesyntax.models.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        Integer oldQuantity = Storage.fruitMap.getOrDefault(fruitTransaction.getFruit(), 0);
        if (oldQuantity - fruitTransaction.getQuantity() < 0
                || fruitTransaction.getQuantity() < 0) {
            throw new NegativeQuantityException(
                "Insufficient quantity of fruits in storage to buy");
        }
        Storage.fruitMap.put(fruitTransaction.getFruit(),
                oldQuantity - fruitTransaction.getQuantity());
    }
}
