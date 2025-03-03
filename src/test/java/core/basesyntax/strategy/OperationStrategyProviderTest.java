package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exception.OperationException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyProviderTest {
    private OperationStrategyProvider provider;

    @BeforeEach
    void setUp() {
        provider = new OperationStrategyProvider();
    }

    @Test
    void testGetHandlerForBalance() {
        OperationHandler handler = provider.getHandler(FruitTransaction.OperationType.BALANCE);
        assertNotNull(handler);
        assertTrue(handler instanceof BalanceOperationHandler);
    }

    @Test
    void testGetHandlerForSupply() {
        OperationHandler handler = provider.getHandler(FruitTransaction.OperationType.SUPPLY);
        assertNotNull(handler);
        assertTrue(handler instanceof AddOperationHandler.SupplyOperationHandler);
    }

    @Test
    void testGetHandlerForPurchase() {
        OperationHandler handler = provider.getHandler(FruitTransaction.OperationType.PURCHASE);
        assertNotNull(handler);
        assertTrue(handler instanceof PurchaseOperationHandler);
    }

    @Test
    void testGetHandlerForReturn() {
        OperationHandler handler = provider.getHandler(FruitTransaction.OperationType.RETURN);
        assertNotNull(handler);
        assertTrue(handler instanceof ReturnOperationHandler);
    }

    @Test
    void testGetHandlerForUnknownOperation() {
        OperationException exception = assertThrows(OperationException.class,
                () -> provider.getHandler(null));
        assertEquals("Unknown operation type: null", exception.getMessage());
    }

    @Test
    void testGetOperationStrategySize() {
        assertEquals(4, provider.getOperationStrategy().size());
    }
}
