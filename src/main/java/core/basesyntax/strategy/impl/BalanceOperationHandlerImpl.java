package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;

public class BalanceOperationHandlerImpl implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction transaction,
                                FruitStorage fruitStorage, List<String> errorMessages) {
        String fruitName = transaction.getFruit();
        int quantity = transaction.getQuantity();

        if (fruitStorage.getFruit(fruitName) == null) {
            fruitStorage.addFruit(new Fruit(fruitName, quantity));
        } else {
            Fruit fruit = fruitStorage.getFruit(fruitName);
            fruit.setQuantity(quantity);
        }
    }
}
