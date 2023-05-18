package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.AdditionalOperation;
import core.basesyntax.strategy.OperationHendler;
import core.basesyntax.strategy.SubtractionOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHendler> operationHandlerMap;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new AdditionalOperation());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, new SubtractionOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void testGet_ValidOperation_ReturnsOperationHandler() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        OperationHendler expectedHandler = operationHandlerMap.get(operation);
        OperationHendler actualHandler = operationStrategy.get(operation);
        assertNotNull(actualHandler);
        assertEquals(expectedHandler, actualHandler);
    }

    @Test
    public void testGet_InvalidOperation_ReturnsNull() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        OperationHendler actualHandler = operationStrategy.get(operation);
        assertNull(actualHandler);
    }
}
