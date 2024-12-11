package core.basesyntax.data.strategy;

import core.basesyntax.data.model.FruitTransaction;

public interface OperationHandler {

    void handle(FruitTransaction operation);
}
