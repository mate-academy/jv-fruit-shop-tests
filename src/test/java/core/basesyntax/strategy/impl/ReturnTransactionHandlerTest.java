package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.StorageTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnTransactionHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final Integer START_QUANTITY = 100;
    private static final Integer RETURN_QUANTITY = 50;
    private static final StorageTransaction.Operation RETURN =
            StorageTransaction.Operation.RETURN;
    private static TransactionHandler returnHandler;
    private static StorageTransaction transaction;

    @BeforeClass
    public static void setUp() {
        returnHandler = new ReturnTransactionHandler();
        transaction = new StorageTransaction(RETURN, FRUIT_NAME, RETURN_QUANTITY);
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
    }

    @Test
    public void handle_changeQuantity_ok() {
        returnHandler.handle(transaction);
        int expected = START_QUANTITY + RETURN_QUANTITY;
        int actual = Storage.storage.get(FRUIT_NAME);
        assertEquals("Expected quantity: " + " but was " + actual, expected, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
