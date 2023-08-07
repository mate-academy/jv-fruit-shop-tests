package core.basesyntax.service.strategy;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.FruitStorage;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (FruitStorage.STORAGE_MAP.get(fruitTransaction.getFruit()) != null) {
            throw new InvalidDataException("Balance cannot be changed during the day");
        }
        FruitStorage.STORAGE_MAP.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
