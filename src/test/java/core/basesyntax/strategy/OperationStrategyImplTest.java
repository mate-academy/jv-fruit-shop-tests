package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.operation.OperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;

    @BeforeEach
    public void setUp() {
        balanceHandler = mock(OperationHandler.class);
        purchaseHandler = mock(OperationHandler.class);

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, balanceHandler,
                FruitTransaction.Operation.PURCHASE, purchaseHandler
        );

        operationStrategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    public void testGetOperationHandlerForBalanceOperation_ok() {
        OperationHandler handler =
                operationStrategy.getOperationHandler(FruitTransaction.Operation.BALANCE);

        assertNotNull(handler, "Handler should not be null");
        assertEquals(balanceHandler, handler, "Should return the balance handler");
    }

    @Test
    public void testGetOperationHandlerForPurchaseOperation_ok() {
        OperationHandler handler =
                operationStrategy.getOperationHandler(FruitTransaction.Operation.PURCHASE);

        assertNotNull(handler, "Handler should not be null");
        assertEquals(purchaseHandler, handler, "Should return the purchase handler");
    }

    @Test
    public void testGetOperationHandlerForUnsupportedOperation_notOk() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> operationStrategy.getOperationHandler(FruitTransaction.Operation.RETURN),
                "Expected exception for unsupported operation"
        );

        assertTrue(exception.getMessage().contains("No handler found for operation: RETURN"),
                "Exception message should mention missing handler for RETURN operation");
    }
}
