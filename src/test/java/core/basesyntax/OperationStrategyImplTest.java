package core.basesyntax;

import core.basesyntax.impl.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private final OperationHandler balanceHandler = Mockito.mock(OperationHandler.class);
    private final OperationHandler purchaseHandler = Mockito.mock(OperationHandler.class);
    private final OperationHandler returnHandler = Mockito.mock(OperationHandler.class);
    private final OperationHandler supplyHandler = Mockito.mock(OperationHandler.class);

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseHandler);
        handlers.put(FruitTransaction.Operation.RETURN, returnHandler);
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyHandler);
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getOperationHandler_validOperations_ok() {
        Assertions.assertEquals(balanceHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE));
        Assertions.assertEquals(purchaseHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE));
        Assertions.assertEquals(returnHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN));
        Assertions.assertEquals(supplyHandler, operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getOperationHandler_invalidOperation_notOk() {
        Exception exception = Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationHandler(null));
        Assertions.assertTrue(exception
                .getMessage()
                .contains("Operation doesn't exist"));
    }
}
