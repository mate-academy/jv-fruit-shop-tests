package core.basesyntax.serviceimpltest;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategyimpl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {

    @Test
    void executeOperation_validOperation_shouldCallHandler() {
        AtomicBoolean called = new AtomicBoolean(false);
        OperationHandler operationHandler = new OperationHandler() {

            @Override
            public void handle(String fruit, int quantity) {
                called.set(true);
            }
        };

        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.SUPPLY, operationHandler);

        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "apple", 10);
        strategy.executeOperation(transaction, new HashMap<>());
        Assertions.assertTrue(called.get(), "Handler should be called");
    }

    @Test
    void executeOperation_unsupportedOperation_shouldThrowException() {
        // arrange
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            strategy.executeOperation(transaction, new HashMap<>());
        });
    }
}
