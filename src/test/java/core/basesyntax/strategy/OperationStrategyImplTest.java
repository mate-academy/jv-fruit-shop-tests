package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransactionImpl;
import core.basesyntax.operationhandlers.BalanceOperationHandler;
import core.basesyntax.operationhandlers.OperationHandler;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperationHandler();
        Map<FruitTransactionImpl.Operation, OperationHandler> handlers =
                new EnumMap<>(FruitTransactionImpl.Operation.class);
        handlers.put(FruitTransactionImpl.Operation.BALANCE, balanceHandler);

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_ValidOperation_ShouldReturnHandler() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransactionImpl.Operation.BALANCE);
        assertNotNull(handler, "Handler should not be null for valid operation");
    }

    @Test
    void getHandler_InvalidOperation_ShouldReturnNull() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransactionImpl.Operation.PURCHASE);
        assertNull(handler, "Handler should be null for an unknown operation");
    }
}
