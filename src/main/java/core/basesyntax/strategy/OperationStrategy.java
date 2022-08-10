package core.basesyntax.strategy;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operations.Operation;

public interface OperationStrategy {
    Operation get(FruitTransaction.Activity activity);
}
