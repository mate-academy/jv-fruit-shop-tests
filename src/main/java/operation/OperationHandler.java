package operation;

import model.FruitTransaction;

public interface OperationHandler {
    OperationHandler handle(FruitTransaction fruitTransaction);
}
