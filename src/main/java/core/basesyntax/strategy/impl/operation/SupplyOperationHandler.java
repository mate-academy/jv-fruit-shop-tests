package core.basesyntax.strategy.impl.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strorage.FruitStorage;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (FruitStorage.fruits.containsKey(transaction.getFruit())) {
            FruitStorage.fruits.put(transaction.getFruit(),
                    FruitStorage.fruits.get(transaction.getFruit())
                            + transaction.getQuantity());
        } else {
            FruitStorage.fruits.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
