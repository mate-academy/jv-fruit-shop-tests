package core.basesyntax.strategy;

import static core.basesyntax.Operation.BALANCE;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new PurchaseHandler();
    }

    @Test
    public void handle_WithNullContent_ThrowException() {
        assertThrows(RuntimeException.class, () -> handler.handle(null));
    }

    @Test
    public void handle_NonExistentFruit_ThrowException() {
        FruitTransaction transaction = new FruitTransaction(BALANCE, "Lemon", 10);
        assertThrows(RuntimeException.class, () -> handler.handle(transaction));
    }

    @Test
    public void handle_UpdateExistingFruitQuantity_Equals() {
        Storage.storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(BALANCE, "banana", 2);
        handler.handle(transaction);
        assertEquals(8, Storage.storage.get("banana"));
    }
}
