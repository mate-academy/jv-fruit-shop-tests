package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceHandlerImpl());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseHandlerImpl());
        operationHandlerMap.put(Operation.RETURN, new ReturnHandlerImpl());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void getHandlerBalanceOperation_Ok() {
        Class<? extends OperationHandler> expectedClass = BalanceHandlerImpl.class;
        Operation testOperation = Operation.BALANCE;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerReturnOperation_Ok() {
        Class<? extends OperationHandler> expectedClass = ReturnHandlerImpl.class;
        Operation testOperation = Operation.RETURN;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerPurchaseOperation_Ok() {
        Class<? extends OperationHandler> expectedClass = PurchaseHandlerImpl.class;
        Operation testOperation = Operation.PURCHASE;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }

    @Test
    public void getHandlerSupplyOperation_Ok() {
        Class<? extends OperationHandler> expectedClass = SupplyHandlerImpl.class;
        Operation testOperation = Operation.SUPPLY;
        Class<? extends OperationHandler> actual =
                operationStrategy.getHandler(testOperation).getClass();

        assertEquals(expectedClass, actual);
    }
}
