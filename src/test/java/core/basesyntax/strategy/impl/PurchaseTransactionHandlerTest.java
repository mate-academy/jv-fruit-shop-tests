package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.Storage;
import core.basesyntax.model.StorageTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final Integer START_QUANTITY = 100;
    private static final Integer PURCHASE_QUANTITY = 30;
    private static final Integer HIGH_PURCHASE_QUANTITY = 120;
    private static final StorageTransaction.Operation PURCHASE =
            StorageTransaction.Operation.PURCHASE;
    private static TransactionHandler purchaseHandler;
    private static StorageTransaction transaction;

    @Before
    public void setUp() throws Exception {
        purchaseHandler = new PurchaseTransactionHandler();
        transaction = new StorageTransaction(PURCHASE, FRUIT_NAME, PURCHASE_QUANTITY);
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
    }

    @Test(expected = RuntimeException.class)
    public void handle_purchaseQuantityIsBiggerThanBalance_notOk() {
        transaction = new StorageTransaction(PURCHASE, FRUIT_NAME, HIGH_PURCHASE_QUANTITY);
        purchaseHandler.handle(transaction);
        fail("You should throw RuntimeException when purchase quantity is bigger than balance");
    }

    @Test
    public void handle_changeQuantity_ok() {
        int expected = START_QUANTITY - PURCHASE_QUANTITY;
        purchaseHandler.handle(transaction);
        int actual = Storage.storage.get(FRUIT_NAME);
        assertEquals("Expected quantity: " + expected + "but was " + actual,
                expected, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
