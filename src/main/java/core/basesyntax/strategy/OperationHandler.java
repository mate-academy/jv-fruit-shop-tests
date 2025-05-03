package core.basesyntax.strategy;

import core.basesyntax.fruitscontent.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction transaction);
}
