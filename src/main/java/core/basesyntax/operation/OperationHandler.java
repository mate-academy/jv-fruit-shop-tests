package core.basesyntax.operation;

import core.basesyntax.model.Fruit;

public interface OperationHandler {
    boolean apply(Fruit fruit, int quantity);
}
