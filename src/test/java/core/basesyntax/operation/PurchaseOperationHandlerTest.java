package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String FRUIT = "apple";
    private StorageService storageService;
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(storageService);
    }

    @Test
    public void handle_Ok() {
        storageService.updateBalance(FRUIT, 10);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT);
        transaction.setAmount(5);

        purchaseOperationHandler.handle(transaction, new HashMap<>());

        assertEquals(5, storageService.getStorage().get(FRUIT).intValue());
    }

    @Test
    public void handle_NotEnoughFruit_NotOk() {
        storageService.updateBalance(FRUIT, 3);

        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit(FRUIT);
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
