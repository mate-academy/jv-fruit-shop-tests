package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyOperation();
        OperationHandler purchaseHandler = new PurchaseOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_validOperation_success() {
        OperationHandler handler = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler);
        assertEquals(supplyHandler, handler);
    }

    @Test
    void getHandler_invalidOperation_throwsException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertEquals("No handler found for operation: BALANCE", thrown.getMessage());
    }
}
