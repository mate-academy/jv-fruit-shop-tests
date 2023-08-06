package core.basesyntax;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operators.BalanceOperationHandler;
import core.basesyntax.operators.OperationHandler;
import core.basesyntax.operators.PurchaseOperationHandler;
import core.basesyntax.operators.ReturnOperationHandler;
import core.basesyntax.operators.SupplyOperationHandler;
import core.basesyntax.service.OperatorStrategy;
import core.basesyntax.service.impl.OperatorStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperatorsAndStrategyTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationToHandlerMap
            = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    private OperatorStrategy operatorStrategy;
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        operatorStrategy = new OperatorStrategyImpl(operationToHandlerMap);
    }

    @Test
    public void getOperatorHandler_BalanceOperation_Ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.BALANCE);
        Assertions.assertTrue(
                handler instanceof BalanceOperationHandler,
                "Handler should be BalanceOperationHandler for BALANCE operation");
    }

    @Test
    public void getOperatorHandler_PurchaseOperation_Ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.PURCHASE);
        Assertions.assertTrue(handler instanceof PurchaseOperationHandler,
                "Handler should be PurchaseOperationHandler for PURCHASE operation");
    }

    @Test
    public void getOperatorHandler_ReturnOperation_Ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.RETURN);
        Assertions.assertTrue(handler instanceof ReturnOperationHandler,
                "Handler should be ReturnOperationHandler for RETURN operation");
    }

    @Test
    public void getOperatorHandler_SupplyOperation_Ok() {
        OperationHandler handler
                = operatorStrategy.getOperatorHandler(FruitTransaction.Operation.SUPPLY);
        Assertions.assertTrue(handler instanceof SupplyOperationHandler,
                "Handler should be SupplyOperationHandler for SUPPLY operation");
    }

    @Test
    public void balance_ValidData_Ok() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("banana", 20);
        assertTrue(Storage.storage.containsKey("banana"));
        assertEquals(20, (int) Storage.storage.get("banana"), "Balance should be 20 for 'banana'");
        Storage.storage.clear();
    }

    @Test
    public void balance_ZeroBalance_AddedToStorage() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("apple", 0);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(0, actualBalance, "Balance should be 0 for 'apple' after balance operation");
        Storage.storage.clear();
    }

    @Test
    public void balance_NegativeBalance_AddedToStorage() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("banana", -10);
        int actualBalance = Storage.storage.getOrDefault("banana", 0);
        assertEquals(-10,
                actualBalance, "Balance should be -10 for 'banana' after balance operation");
        Storage.storage.clear();
    }

    @Test
    public void purchase_EnoughStock_Ok() {
        Storage.storage.put("banana", 50);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.execute("banana", 20);
        int actualBalance = Storage.storage.getOrDefault("banana", 0);
        assertEquals(30, actualBalance, "Balance should be 30 for 'banana' after purchase");
        Storage.storage.clear();
    }

    @Test
    public void purchase_NotEnoughStock_NotOk() {
        Storage.storage.put("apple", 5);
        operationHandler = new PurchaseOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.execute("apple", 10),
                "Not enough apple available in the storage. Available: 5.");
        Storage.storage.clear();
    }

    @Test
    public void purchase_RemoveProduct_Ok() {
        Storage.storage.put("orange", 5);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.execute("orange", 5);
        boolean isProductExists = Storage.storage.containsKey("orange");
        assertFalse(isProductExists, "Product 'orange' should be removed from the storage.");
        Storage.storage.clear();
    }

    @Test
    public void return_ValidData_Ok() {
        Storage.storage.put("apple", 40);
        operationHandler = new ReturnOperationHandler();
        operationHandler.execute("apple", 10);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(50, actualBalance, "Balance should be 50 for 'apple' after return");
        Storage.storage.clear();
    }

    @Test
    public void supply_ValidData_Ok() {
        operationHandler = new SupplyOperationHandler();
        operationHandler.execute("apple", 50);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(50, actualBalance, "Balance should be 50 for 'apple' after supply");
        Storage.storage.clear();
    }
}
