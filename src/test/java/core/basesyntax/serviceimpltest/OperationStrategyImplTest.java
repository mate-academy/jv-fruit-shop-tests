package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategyimpl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    @Test
    void executeOperation_validOperation_shouldCallHandler() {
        boolean[] called = {false};
        OperationHandler operationHandler = (fruit, quantity) -> called[0] = true;
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.SUPPLY, operationHandler);
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        strategy.executeOperation(transaction, new HashMap<>());
        assertEquals(true, called[0], "Handler should be called");
    }

    @Test
    void executeOperation_unsupportedOperation_shouldThrowException() {
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        assertThrows(UnsupportedOperationException.class,
                () -> strategy.executeOperation(transaction, new HashMap<>()));
    }
}
