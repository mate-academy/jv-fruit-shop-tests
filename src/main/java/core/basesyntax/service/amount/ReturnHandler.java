package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (Storage.fruits.isEmpty()) {
            Storage.fruits.put(fruitTransaction.getFruitType(), fruitTransaction.getAmount());
            return;
        }
        if (fruitTransaction.getAmount() < 0) {
            throw new RuntimeException("You can`t return less than 0 fruits");
        }
        Storage.fruits.compute(fruitTransaction.getFruitType(),
                (k, v) -> v + fruitTransaction.getAmount());
    }
}
