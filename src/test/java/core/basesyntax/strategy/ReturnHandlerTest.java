package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static core.basesyntax.Operation.BALANCE;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class ReturnHandlerTest {

    private OperationHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new ReturnHandler();
    }

    @Test
    void testWriteToStorageWithNullContent() {
        assertThrows(RuntimeException.class, () -> handler.handle(null));
    }

    @Test
    public void testHandleAddNewFruitToStorage() {
        FruitTransaction transaction = new FruitTransaction(BALANCE, "Lemon", 10);
        handler.handle(transaction);
        assertEquals(10, Storage.storage.get("Lemon"));
    }

    @Test
    public void testHandleUpdateExistingFruitQuantity() {
        Storage.storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(BALANCE, "banana", 2);
        handler.handle(transaction);
        assertEquals(12, Storage.storage.get("banana"));
    }
}