package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.Calculator;

public interface OperationStrategy {
    Calculator get(Fruit.OperationType type);
}
