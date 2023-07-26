package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int apply(FruitTransaction fruitTransaction) {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        return fruitTransaction.getQuantity();
    }
}
