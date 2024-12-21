package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler supplyHandler;
    private OperationHandler returnHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = (fruit, quantity) -> {};
        purchaseHandler = (fruit, quantity) -> {};
        supplyHandler = (fruit, quantity) -> {};
        returnHandler = (fruit, quantity) -> {};

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationHandlers.put(FruitTransaction.Operation.RETURN, returnHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_validOperation_ok() {
        assertEquals(balanceHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
        assertEquals(purchaseHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE));
        assertEquals(supplyHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY));
        assertEquals(returnHandler,
                operationStrategy.getHandler(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getHandler_invalidOperation_returnsNull() {
        assertNull(operationStrategy.getHandler(null));
    }

    @Test
    void getHandler_noHandlersProvided_returnsNull() {
        OperationStrategyImpl emptyStrategy = new OperationStrategyImpl(new HashMap<>());
        assertNull(emptyStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }
}
