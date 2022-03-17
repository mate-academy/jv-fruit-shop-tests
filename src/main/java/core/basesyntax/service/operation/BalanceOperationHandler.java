package core.basesyntax.service.operation;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exceptions.RepeatedBalanceOperatorException;
import core.basesyntax.model.Fruit;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void doOperation(Fruit fruit) {
        if (fruit == null) {
            throw new RuntimeException("Can't do any operation with null fruit");
        }
        if (FruitStorage.fruits.contains(fruit)) {
            throw new RepeatedBalanceOperatorException(fruit);
        }
        FruitStorage.fruits.add(fruit);
    }
}
