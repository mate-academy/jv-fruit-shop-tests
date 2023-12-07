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

class BalanceHandlerTest {
    private static OperationHandler balanceHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void handleOperation_correctBalanceOfFruit_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(50);
        fruitTransaction.setFruit("banana");
        balanceHandler.handleOperation(fruitTransaction);
        int expected = 50;
        assertEquals(expected, Storage.STORAGE.get("banana"));
    }

    @Test
    void handleOperation_inputObjectWithNullType_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(50);
        fruitTransaction.setFruit(null);
        assertThrows(RuntimeException.class, () ->
                balanceHandler.handleOperation(fruitTransaction));
    }

    @Test
    void handleOperation_balanceWithNegativeAmountOfFruit_NotOk() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(-30);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class, () ->
                balanceHandler.handleOperation(fruitTransaction));
    }
}
