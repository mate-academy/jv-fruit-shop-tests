package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.OperationHandlerStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationHandlerStrategyTest {
    private static OperationHandlerStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, OperationHandler> strategies = new HashMap<>();
        strategies.put(Operation.BALANCE, new BalanceOperationHandler());
        strategies.put(Operation.SUPPLY, new SupplyOperationHandler());
        strategies.put(Operation.RETURN, new ReturnOperationHandler());
        strategies.put(Operation.PURCHASE, new PurchaseOperationHandler());
        strategy = new OperationHandlerStrategyImpl(strategies);
    }

    @Test
    public void getOperationService_balanceOperation_Ok() {
        OperationHandler expectedHandler = new BalanceOperationHandler();
        OperationHandler actualHandler = strategy.getOperationService(Operation.BALANCE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getOperationService_supplyOperation_Ok() {
        OperationHandler expectedHandler = new SupplyOperationHandler();
        OperationHandler actualHandler = strategy.getOperationService(Operation.SUPPLY);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getOperationService_returnOperation_Ok() {
        OperationHandler expectedHandler = new ReturnOperationHandler();
        OperationHandler actualHandler = strategy.getOperationService(Operation.RETURN);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getOperationService_purchaseOperation_Ok() {
        OperationHandler expectedHandler = new PurchaseOperationHandler();
        OperationHandler actualHandler = strategy.getOperationService(Operation.PURCHASE);
        assertEquals(expectedHandler.getClass(), actualHandler.getClass());
    }

    @Test
    public void getOperationService_unknownOperation_notOk() {
        OperationHandler actualHandler = strategy.getOperationService(Operation.UNKNOWN);
        assertNull(actualHandler);
    }

    @Test
    public void handle_validFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, "Apple", 10);
        strategy.getOperationService(fruitTransaction.getOperation());
    }

    @Test
    public void handle_invalidFruitTransaction_ThrowsException() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE, null, 5);
        strategy.getOperationService(fruitTransaction.getOperation());
    }
}
