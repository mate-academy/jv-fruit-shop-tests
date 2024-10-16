package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private StorageService storageService;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        storageService = new StorageServiceImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        storageService.updateBalance("apple", 10);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setAmount(5);

        purchaseOperationHandler.handle(transaction, new HashMap<>());

        assertEquals(5, storageService.getStorage().get("apple").intValue());
    }

    @Test
    public void handle_NotEnoughFruit_NotOk() {
        storageService.updateBalance("banana", 3);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setAmount(5);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction, new HashMap<>());
        });
        assertEquals("Not enough fruit in stock to complete the purchase.", exception.getMessage());
    }

    @Test
    public void handle_NoSuchFruit_NotOk() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("orange");
        transaction.setAmount(1);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.handle(transaction, new HashMap<>());
        });
        assertEquals("No such fruit in stock.", exception.getMessage());
    }
}
