package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private Storage storage;
    private PurchaseHandler purchaseHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        purchaseHandler = new PurchaseHandler();
        transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,null, 0);
    }

    @Test
    public void purchaseHandler_testOperateTransaction_ok() {
        transaction.setQuantity(3);

        storage.put(transaction.getFruit(), 5);

        purchaseHandler.operateTransaction(transaction, storage);

        int expectedQuantity = 2; // 5 - 3
        int actualQuantity = storage.get(transaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void purchaseHandler_NegativeQuantity_ok() {
        transaction.setQuantity(10);
        storage.put(transaction.getFruit(), 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> purchaseHandler.operateTransaction(transaction, storage));
        String expectedMessage = "Don't have enough fruits.";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
