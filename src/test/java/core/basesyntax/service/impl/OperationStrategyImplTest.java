package core.basesyntax.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.strategy.BalanceHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private FruitDao fruitDao;

    @BeforeEach
    public void setUp() {
        Map<Fruit.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Fruit.Operation.BALANCE, new BalanceHandler(fruitDao));
        operationHandlerMap.put(Fruit.Operation.PURCHASE, new PurchaseHandler(fruitDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void testGetBalanceHandler() {
        OperationHandler handler = operationStrategy.get(Fruit.Operation.BALANCE);
        assertNotNull(handler);
        assertTrue(handler instanceof BalanceHandler);
    }

    @Test
    public void testUnsupportedOperation() {
        assertThrows(IllegalArgumentException.class, ()
                -> operationStrategy.get(Fruit.Operation.valueOf("UNKNOWN")));
    }
}
