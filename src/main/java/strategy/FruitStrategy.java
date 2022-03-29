package strategy;

import model.FruitTransaction;
import operation.OperationHandler;

public interface FruitStrategy {
    OperationHandler proceed(FruitTransaction fruitTransaction);
}
