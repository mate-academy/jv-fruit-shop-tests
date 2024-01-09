package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.InputOperation;

public interface OperationStrategy {
    InputOperation get(FruitTransaction.Operation operation);
}
