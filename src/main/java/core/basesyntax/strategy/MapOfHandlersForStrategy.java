package core.basesyntax.strategy;

import core.basesyntax.servise.impl.FruitTransaction;
import java.util.Map;

public interface MapOfHandlersForStrategy {
    Map<FruitTransaction.Operation, OperationService> getHandlers();

    void putHandler(FruitTransaction.Operation operation, OperationService operationService);

    void removeHandler(FruitTransaction.Operation operation);
}
