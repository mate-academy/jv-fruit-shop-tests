package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategyImpl operationStrategy;

    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = mock(OperationHandler.class);
        supplyHandler = mock(OperationHandler.class);
        purchaseHandler = mock(OperationHandler.class);
        returnHandler = mock(OperationHandler.class);

        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlerMap.put(FruitTransaction.Operation.RETURN, returnHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperationHandler_balance_OK() {
        OperationHandler result = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        assertEquals(balanceHandler, result, "Expected BALANCE handler to be returned.");
    }

    @Test
    void getOperationHandler_supply_OK() {
        OperationHandler result = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        assertEquals(supplyHandler, result, "Expected SUPPLY handler to be returned.");
    }

    @Test
    void getOperationHandler_purchase_OK() {
        OperationHandler result = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);
        assertEquals(purchaseHandler, result, "Expected PURCHASE handler to be returned.");
    }

    @Test
    void getOperationHandler_return_OK() {
        OperationHandler result = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN);
        assertEquals(returnHandler, result, "Expected RETURN handler to be returned.");
    }

    @Test
    void getOperationHandler_invalidOperation_notOK() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.getOperationHandler(null)
        );
        assertEquals("No handler found for operation: null", exception.getMessage());
    }

    @Test
    void getOperationHandler_unmappedOperation_notOK() {
        operationHandlerMap.remove(FruitTransaction.Operation.RETURN);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN)
        );
        assertEquals("No handler found for operation: RETURN", exception.getMessage());
    }
}
