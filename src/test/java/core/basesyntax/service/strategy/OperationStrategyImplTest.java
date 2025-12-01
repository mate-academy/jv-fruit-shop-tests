package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategy strategy;

    @BeforeEach
    void setUp() {
        handlers = new EnumMap<>(FruitTransaction.Operation.class);
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        strategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_balanceOperation_ok() {
        OperationHandler handler = strategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
        assertTrue(handler instanceof BalanceOperation);
    }

    @Test
    void get_supplyOperation_ok() {
        OperationHandler handler = strategy.get(FruitTransaction.Operation.SUPPLY);
        assertNotNull(handler);
        assertTrue(handler instanceof SupplyOperation);
    }

    @Test
    void get_purchaseOperation_ok() {
        OperationHandler handler = strategy.get(FruitTransaction.Operation.PURCHASE);
        assertNotNull(handler);
        assertTrue(handler instanceof PurchaseOperation);
    }

    @Test
    void get_returnOperation_ok() {
        OperationHandler handler = strategy.get(FruitTransaction.Operation.RETURN);
        assertNotNull(handler);
        assertTrue(handler instanceof ReturnOperation);
    }

    @Test
    void get_missingOperation_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> emptyHandlers = new HashMap<>();
        OperationStrategy emptyStrategy = new OperationStrategyImpl(emptyHandlers);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emptyStrategy.get(FruitTransaction.Operation.BALANCE));
        assertTrue(exception.getMessage().contains("No handler"));
    }

    @Test
    void get_sameHandlerReturnedMultipleTimes_ok() {
        OperationHandler handler1 = strategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler handler2 = strategy.get(FruitTransaction.Operation.BALANCE);
        assertSame(handler1, handler2);
    }
}
