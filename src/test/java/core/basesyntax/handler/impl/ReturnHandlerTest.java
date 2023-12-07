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

class ReturnHandlerTest {
    private static OperationHandler returnHandler;
    private static FruitTransaction fruitTransaction;
    private static final int DEFAULT_AMOUNT_OF_FRUITS = 90;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", DEFAULT_AMOUNT_OF_FRUITS);
        Storage.STORAGE.put("apple", DEFAULT_AMOUNT_OF_FRUITS);
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        fruitTransaction = new FruitTransaction();
        Storage.STORAGE.clear();
    }

    @Test
    void handleOperation_correctReturnOfFruits_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(10);
        fruitTransaction.setFruit("banana");
        returnHandler.handleOperation(fruitTransaction);
        int expected = 100;
        assertEquals(expected, Storage.STORAGE.get("banana"));
    }

    @Test
    void handleOperation_returnZeroItemsOfFruit_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(0);
        fruitTransaction.setFruit("apple");
        assertThrows(RuntimeException.class, () ->
                returnHandler.handleOperation(fruitTransaction));
    }

    @Test
    void handleOperation_returnFruitWithNullType_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(20);
        fruitTransaction.setFruit(null);
        assertThrows(RuntimeException.class, () ->
                returnHandler.handleOperation(fruitTransaction));
    }
}
