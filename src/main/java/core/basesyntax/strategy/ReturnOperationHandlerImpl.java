package core.basesyntax.strategy;

import static core.basesyntax.storage.Storage.dataBase;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Fruit fruit = fruitTransaction.getFruit();
        dataBase.put(fruit, fruitTransaction.getQuantity() + dataBase.get(fruit));
    }
}
