package core.basesyntax.testclasses;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.BALANCE;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import java.util.Map;

public class MapOfHandlersForTest implements MapOfHandlersForStrategy {
    private final Map<FruitTransaction.Operation, OperationService> mapForTest =
            Map.of(BALANCE, new OperationServiceForTest());

    @Override
    public Map<FruitTransaction.Operation, OperationService> getHandlers() {
        return mapForTest;
    }

    @Override
    public void putHandler(FruitTransaction.Operation operation,
                           OperationService operationService) {
    }

    @Override
    public void removeHandler(FruitTransaction.Operation operation) {
    }
}
