package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.model.FruitTransaction;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers =
                new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_existingOperation_returnCorrectHandler() {
        OperationHandler handler = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler);
        assertTrue(handler instanceof SupplyOperationHandler);
    }

    @Test
    void get_nonExistingOperationIncludeNull_notOk() {
        Map<FruitTransaction.Operation, OperationHandler> emptyHandlers =
                new EnumMap<>(FruitTransaction.Operation.class);
        OperationStrategyImpl emptyStrategy = new OperationStrategyImpl(emptyHandlers);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> emptyStrategy.get(FruitTransaction.Operation.BALANCE));
        assertTrue(exception.getMessage().contains("Unknown operation"));
    }
}
