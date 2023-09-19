package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
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
    void calculateOperation_ok() {
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
}
