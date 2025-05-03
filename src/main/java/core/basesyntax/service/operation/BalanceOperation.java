package core.basesyntax.service.operation;

import core.basesyntax.service.FruitStorage;
import core.basesyntax.service.FruitTransaction;

public class BalanceOperation implements OperationHandler {

    @Override
    public FruitStorage operation(FruitTransaction fruitTransaction, FruitStorage fruitStorage) {

        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit value is null");
        }

        fruitStorage.setFruit(fruitTransaction.getFruit());
        fruitStorage.setQuantity(fruitTransaction.getQuantity());
        return fruitStorage;
    }
}
