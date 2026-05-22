package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction transaction);
}
