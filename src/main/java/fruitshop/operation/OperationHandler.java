package fruitshop.operation;

import fruitshop.model.FruitTransaction;

public interface OperationHandler {
    void accept(FruitTransaction fruitTransaction);
}
