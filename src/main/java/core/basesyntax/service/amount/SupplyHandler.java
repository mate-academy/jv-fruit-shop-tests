package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (Storage.fruits.isEmpty()) {
            Storage.fruits.put(fruitTransaction.getFruitType(), fruitTransaction.getAmount());
            return;
        }
        Storage.fruits.compute(fruitTransaction.getFruitType(),
                (k, v) -> v + fruitTransaction.getAmount());
    }
}
