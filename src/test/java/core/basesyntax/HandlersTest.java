package core.basesyntax;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.operators.BalanceOperationHandler;
import core.basesyntax.operators.OperationHandler;
import core.basesyntax.operators.PurchaseOperationHandler;
import core.basesyntax.operators.ReturnOperationHandler;
import core.basesyntax.operators.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandlersTest {
    private OperationHandler operationHandler;

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void balance_validData_ok() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("banana", 20);
        assertTrue(Storage.storage.containsKey("banana"));
        assertEquals(20, (int) Storage.storage.get("banana"), "Balance should be 20 for 'banana'");
    }

    @Test
    void balance_zeroBalance_addedToStorage() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("apple", 0);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(0, actualBalance, "Balance should be 0 for 'apple' after balance operation");
    }

    @Test
    void balance_negativeBalance_addedToStorage() {
        operationHandler = new BalanceOperationHandler();
        operationHandler.execute("banana", -10);
        int actualBalance = Storage.storage.getOrDefault("banana", 0);
        assertEquals(-10,
                actualBalance, "Balance should be -10 for 'banana' after balance operation");
    }

    @Test
    void purchase_enoughStock_ok() {
        Storage.storage.put("banana", 50);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.execute("banana", 20);
        int actualBalance = Storage.storage.getOrDefault("banana", 0);
        assertEquals(30, actualBalance, "Balance should be 30 for 'banana' after purchase");
    }

    @Test
    void purchase_notEnoughStock_notOk() {
        Storage.storage.put("apple", 5);
        operationHandler = new PurchaseOperationHandler();
        Assertions.assertThrows(RuntimeException.class,
                () -> operationHandler.execute("apple", 10),
                "Not enough apple available in the storage. Available: 5.");
    }

    @Test
    void purchase_removeProduct_ok() {
        Storage.storage.put("orange", 5);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.execute("orange", 5);
        boolean isProductExists = Storage.storage.containsKey("orange");
        assertFalse(isProductExists, "Product 'orange' should be removed from the storage.");
    }

    @Test
    void return_validData_ok() {
        Storage.storage.put("apple", 40);
        operationHandler = new ReturnOperationHandler();
        operationHandler.execute("apple", 10);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(50, actualBalance, "Balance should be 50 for 'apple' after return");
    }

    @Test
    void supply_validData_ok() {
        operationHandler = new SupplyOperationHandler();
        operationHandler.execute("apple", 50);
        int actualBalance = Storage.storage.getOrDefault("apple", 0);
        assertEquals(50, actualBalance, "Balance should be 50 for 'apple' after supply");
    }
}
