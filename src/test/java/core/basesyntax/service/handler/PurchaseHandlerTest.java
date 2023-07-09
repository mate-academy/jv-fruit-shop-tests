package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final int INITIAL_QUANTITY = 10;
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
        Storage.fruits.put(FRUIT_NAME, INITIAL_QUANTITY);
        purchaseHandler = new PurchaseHandler();
    }

    @Test
    void handleTransaction_ValidTransaction_Success() {
        FruitTransaction transaction = createTransaction(FRUIT_NAME, 5);
        purchaseHandler.handleTransaction(transaction);

        assertEquals(INITIAL_QUANTITY - 5, Storage.fruits.get(FRUIT_NAME));
    }

    @Test
    void handleTransaction_NotEnoughFruits_ThrowsException() {
        FruitTransaction transaction = createTransaction(FRUIT_NAME, 15);
        assertThrows(RuntimeException.class, () -> purchaseHandler.handleTransaction(transaction));
    }

    private FruitTransaction createTransaction(String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }

    @AfterEach
    void clearStorage() {
        Storage.fruits.clear();
    }
}
