package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import data.base.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.strategy.OperationHandler;
import service.strategy.hendlers.PurchaseOperationHandler;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", 20);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void updateNumberOfFruit_validQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(19);
        fruitTransaction.setFruit("banana");
        operationHandler.handleOperation(fruitTransaction);
        assertEquals(1, Storage.STORAGE.get("banana"));
    }

    @Test
    void updateNumberOfFruit_notValidQuantity_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(21);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class,
                () -> operationHandler.handleOperation(fruitTransaction));
    }
}