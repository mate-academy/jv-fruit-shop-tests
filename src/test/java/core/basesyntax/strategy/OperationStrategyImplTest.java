package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private Map<Operation, FruitOperationHandler> handlerMap;

    @BeforeEach
    public void setUp() {
        handlerMap = new HashMap<>();
        FruitOperationHandler balanceHandler = new BalanceHandler();
        FruitOperationHandler supplyHandler = new SupplyOperation();
        FruitOperationHandler purchaseHandler = new PurchaseOperation();
        FruitOperationHandler returnHandler = new ReturnOperation();

        handlerMap.put(Operation.BALANCE, balanceHandler);
        handlerMap.put(Operation.SUPPLY, supplyHandler);
        handlerMap.put(Operation.PURCHASE, purchaseHandler);
        handlerMap.put(Operation.RETURN, returnHandler);

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void getHandler_balanceOperation_returnsCorrectHandler() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        Assertions.assertEquals(handlerMap.get(Operation.BALANCE), handler);
    }

    @Test
    public void getHandler_supplyOperation_returnsCorrectHandler() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.SUPPLY);
        Assertions.assertEquals(handlerMap.get(Operation.SUPPLY), handler);
    }

    @Test
    public void getHandler_purchaseOperation_returnsCorrectHandler() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.PURCHASE);
        Assertions.assertEquals(handlerMap.get(Operation.PURCHASE), handler);
    }

    @Test
    public void getHandler_returnOperation_returnsCorrectHandler() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.RETURN);
        Assertions.assertEquals(handlerMap.get(Operation.RETURN), handler);
    }

    @Test
    public void getHandler_nullOperation_shouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null));
    }

    @Test
    public void getHandler_invalidOperation_shouldThrowException() {
        Operation invalidOperation = null;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(invalidOperation));
    }
}
