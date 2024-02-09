package core.basesyntax.strategy;

import static core.basesyntax.Operation.BALANCE;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new SupplyHandler();
    }

    @AfterAll
    public static void tearDoan() {
        Storage.storage.clear();
    }

    @Test
    public void handle_WithNullContent_ThrowException() {
        assertThrows(RuntimeException.class, () -> handler.handle(null));
    }

    @Test
    public void handle_NewFruitToStorage_Ok() {
        FruitTransaction transaction = new FruitTransaction(BALANCE, "Lemon", 10);
        handler.handle(transaction);
        assertEquals(10, Storage.storage.get("Lemon"));
    }

    @Test
    public void handle_ExistingFruitQuantityWithNegativeValue_Ok() {
        Storage.storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(BALANCE, "banana", -2);
        handler.handle(transaction);
        assertEquals(8, Storage.storage.get("banana"));
    }
}
