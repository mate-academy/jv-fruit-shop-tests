package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void beforeEach() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new MockedHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_getStrategy_ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(MockedHandler.class, actual.getClass());
    }

    static class MockedHandler implements OperationHandler {
        @Override
        public void handle(FruitTransaction transaction, ShopStorage storage) {
            return;
        }
    }
}
