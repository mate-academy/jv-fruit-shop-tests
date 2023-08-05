package core.basesyntax.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static OperationHandler supplyHandler;
    private static FruitTransaction fruitTransaction;
    private static int DEFAULT_AMOUNT_OF_FRUITS = 100;

    @BeforeAll
    static void beforeAll() {
        supplyHandler = new SupplyHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", DEFAULT_AMOUNT_OF_FRUITS);
        Storage.STORAGE.put("apple", DEFAULT_AMOUNT_OF_FRUITS);
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handleOperation_supplyCorrectAmountOfFruits_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(30);
        fruitTransaction.setFruit("banana");
        supplyHandler.handleOperation(fruitTransaction);
        int expected = 130;
        assertEquals(expected, Storage.STORAGE.get("banana"));
    }

    @Test
    void handleOperation_supplyFruitWithNullType_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(1000);
        fruitTransaction.setFruit(null);
        assertThrows(RuntimeException.class, () ->
                supplyHandler.handleOperation(fruitTransaction));
    }

    @Test
    void handleOperation_supplyNegativeAmountOfFruits_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(-15);
        fruitTransaction.setFruit("apple");
        assertThrows(RuntimeException.class, () ->
                supplyHandler.handleOperation(fruitTransaction));
    }
}
