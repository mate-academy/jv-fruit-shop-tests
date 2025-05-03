package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerImplTest {
    private static final String EXCEPTION_MESSAGE = "Exception should be thrown here";
    private PurchaseOperationHandlerImpl purchaseOperationHandler;

    @BeforeEach
    void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandlerImpl();
    }

    @Test
    void handle_validData_Ok() {
        Storage.dataStorage.put("banana", 100);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 25);
        Integer expected = 75;
        purchaseOperationHandler.handle(fruit);
        Integer actual = Storage.dataStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handle_invalidQuantity_NotOk() {
        Storage.dataStorage.put("banana", 76);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -123);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @Test
    void handle_NotEnoughFruit_NotOk() {
        Storage.dataStorage.put("banana", 34);
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 100);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @Test
    void handle_fruitIsAbsent_NotOk() {
        FruitTransaction fruit =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 23);
        assertThrows(RuntimeException.class, () ->
                purchaseOperationHandler.handle(fruit), EXCEPTION_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        Storage.dataStorage.clear();
    }
}
