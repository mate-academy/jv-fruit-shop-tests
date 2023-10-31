package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.excteption.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void addSomeFruit_BalanceOperation_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 120
        );
        assertDoesNotThrow(() -> balanceOperationHandler.calculateOperation(bananaTransaction));
        FruitTransaction appleTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50
        );
        assertDoesNotThrow(() -> balanceOperationHandler.calculateOperation(appleTransaction));
        int expectedBanana = Storage.STORAGE.get("banana");
        assertEquals(expectedBanana, 120);
        int expectedApple = Storage.STORAGE.get("apple");
        assertEquals(expectedApple, 50);
    }

    @Test
    void addFruits_NegativeQuantity_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> balanceOperationHandler.calculateOperation(bananaTransaction)
        );
    }

    @Test
    void addFruits_nullFruit_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, null, 100
        );
        assertThrows(NullPointerException.class,
                () -> balanceOperationHandler.calculateOperation(bananaTransaction)
        );
    }

    @Test
    void addFruits_NullOperationType_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                null, "banana", 100
        );
        assertThrows(NullPointerException.class,
                () -> balanceOperationHandler.calculateOperation(bananaTransaction)
        );
    }
}

