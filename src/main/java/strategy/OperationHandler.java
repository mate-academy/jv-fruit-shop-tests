package strategy;

import model.FruitTransaction;

public interface OperationHandler {
    void handler(FruitTransaction fruitTransaction);
}
