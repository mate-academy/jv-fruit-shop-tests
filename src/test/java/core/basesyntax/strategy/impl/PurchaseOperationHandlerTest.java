package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeAll
    public static void setUpAll() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    public void setUp() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 100);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void performOperation_itemNotExists_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseHandler.performOperation("orange", 50));
    }

    @Test
    public void performOperation_notEnoughItems_notOk() {
        assertThrows(RuntimeException.class, () -> purchaseHandler.performOperation("apple", 101));
    }

    @Test
    public void performOperation_regularCase_ok() {
        purchaseHandler.performOperation("banana", 10);
        purchaseHandler.performOperation("apple", 100);
        assertEquals(10, Storage.fruits.get("banana"));
        assertEquals(0, Storage.fruits.get("apple"));
    }
}
