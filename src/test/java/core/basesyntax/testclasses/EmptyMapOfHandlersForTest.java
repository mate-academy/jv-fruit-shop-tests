package core.basesyntax.testclasses;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import java.util.HashMap;
import java.util.Map;

public class EmptyMapOfHandlersForTest implements MapOfHandlersForStrategy {
    @Override
    public Map<FruitTransaction.Operation, OperationService> getHandlers() {
        return new HashMap<>();
    }

    @Override
    public void putHandler(FruitTransaction.Operation operation,
                           OperationService operationService) {
    }

    @Override
    public void removeHandler(FruitTransaction.Operation operation) {
    }
}
