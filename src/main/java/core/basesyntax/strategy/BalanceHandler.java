package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public int calculate(FruitTransaction fruit) {
        return fruit.getFruitQuantity();
    }
}
