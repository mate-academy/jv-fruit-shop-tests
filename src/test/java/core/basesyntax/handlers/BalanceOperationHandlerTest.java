package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
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
        Storage.storage.clear();
    }

    @Test
    void addSomeFruit_BalanceOperation_Ok() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 120
        );
        balanceOperationHandler.calculateOperation(bananaTransaction);
        FruitTransaction appleTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 50
        );
        balanceOperationHandler.calculateOperation(appleTransaction);
        int expectedBanana = Storage.storage.get("banana");
        assertEquals(120, expectedBanana);
        int expectedApple = Storage.storage.get("apple");
        assertEquals(50, expectedApple);
    }

    @Test
    void addFruits_NegativeQuantity_notOk() {
        FruitTransaction bananaTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> balanceOperationHandler.calculateOperation(bananaTransaction));
    }
}
