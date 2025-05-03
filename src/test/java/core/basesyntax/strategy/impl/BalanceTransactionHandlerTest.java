package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.StorageTransaction;
import core.basesyntax.strategy.TransactionHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTransactionHandlerTest {
    private static final String FRUIT_NAME = "apple";
    private static final Integer FRUIT_QUANTITY = 100;
    private static final StorageTransaction.Operation BALANCE =
            StorageTransaction.Operation.BALANCE;
    private static TransactionHandler balanceHandler;
    private static StorageTransaction transaction;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceTransactionHandler();
        transaction = new StorageTransaction(BALANCE, FRUIT_NAME, FRUIT_QUANTITY);
    }

    @Test
    public void handle_storageIsNotEmpty_ok() {
        balanceHandler.handle(transaction);
        assertFalse("Storage does not be empty. Storage size = " + Storage.storage.size(),
                Storage.storage.isEmpty());
    }

    @Test
    public void handle_storageHasCertainKey_ok() {
        balanceHandler.handle(transaction);
        assertTrue("Storage must have this key" + FRUIT_NAME,
                Storage.storage.containsKey(FRUIT_NAME));
    }

    @Test
    public void handle_storageHasCertainValue() {
        balanceHandler.handle(transaction);
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals("Value must have quantity: " + FRUIT_QUANTITY + " but was " + actual,
                FRUIT_QUANTITY, actual);
    }

    @After
    public void after() {
        Storage.storage.clear();
    }
}
