package core.basesyntax.service.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap
            = new HashMap<>();
    private static final OperationStrategy operationStrategy
            = new OperationStrategyImpl(operationHandlerMap);

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
    }

    @Test
    public void getBalanceOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE).getClass();
        Class<?> expectedClass = BalanceOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getPurchaseOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE).getClass();
        Class<?> expectedClass = PurchaseOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getReturnOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN).getClass();
        Class<?> expectedClass = ReturnOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getSupplyOperationHandler_ok() {
        Class<?> actualClass = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY).getClass();
        Class<?> expectedClass = SupplyOperationHandler.class;
        assertEquals(expectedClass, actualClass);
    }

    @Test(expected = RuntimeException.class)
    public void getOperationHandlerOfNullOperation_notOk() {
        operationStrategy
                .getOperationHandler(null);
    }
}
