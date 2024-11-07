package core.basesyntax.stategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
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
    void getBalanceOperation_OK() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.BALANCE);
        assertEquals(handlerMap.get(Operation.BALANCE), handler);
    }

    @Test
    void getSupplyOperation_OK() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.SUPPLY);
        assertEquals(handlerMap.get(Operation.SUPPLY), handler);
    }

    @Test
    void getPurchaseOperation_OK() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.PURCHASE);
        assertEquals(handlerMap.get(Operation.PURCHASE), handler);
    }

    @Test
    void getReturnOperation_OK() {
        FruitOperationHandler handler = operationStrategy.getHandler(Operation.RETURN);
        assertEquals(handlerMap.get(Operation.RETURN), handler);
    }

    @Test
    void getNullOperation_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.getHandler(null));
    }
}
