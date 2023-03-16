package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.BalanceHandler;
import core.basesyntax.strategy.handler.impl.PurchaseHandler;
import core.basesyntax.strategy.handler.impl.ReturnHandler;
import core.basesyntax.strategy.handler.impl.SupplyHandler;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeClass
    public static void beforeClass() {
        strategy = new OperationStrategyImpl();
    }

    @Test
    public void test_Get_BalanceHandler_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        OperationHandler expectedHandler = new BalanceHandler();
        OperationHandler actualHandler = strategy.getHandler(operation);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void test_Get_SupplyHandler_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.SUPPLY;
        OperationHandler expectedHandler = new SupplyHandler();
        OperationHandler actualHandler = strategy.getHandler(operation);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void test_Get_PurchaseHandler_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        OperationHandler expectedHandler = new PurchaseHandler();
        OperationHandler actualHandler = strategy.getHandler(operation);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void test_Get_ReturnHandler_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;
        OperationHandler expectedHandler = new ReturnHandler();
        OperationHandler actualHandler = strategy.getHandler(operation);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void testGetHandlerThrowsExceptionForInvalidOperation() {
        FruitTransaction.Operation invalidOperation = FruitTransaction.Operation.getOperationByCode("a");
        try {
            strategy.getHandler(invalidOperation);
            Assert.fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Unknown operation: " + invalidOperation, e.getMessage());
        }
    }
}