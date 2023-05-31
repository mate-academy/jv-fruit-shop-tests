package core.basesyntax.service.amount;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        if (Storage.fruits.isEmpty()
                || Storage.fruits.get(fruitTransaction.getFruitType())
                        < fruitTransaction.getAmount()) {
            throw new RuntimeException("You can`t cell more fruits than you have");
        }
        Storage.fruits.compute(fruitTransaction.getFruitType(),
                (k, v) -> v - fruitTransaction.getAmount());
    }
}
