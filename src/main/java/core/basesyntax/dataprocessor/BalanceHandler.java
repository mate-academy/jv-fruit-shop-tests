package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitDB;

public class BalanceHandler implements OperationHandler {
    private final FruitDB fruitDB;

    public BalanceHandler(FruitDB fruitDB) {
        this.fruitDB = fruitDB;
    }

    @Override
    public void apply(String fruit, int quantity) {
        fruitDB.add(fruit, quantity);
    }
}
