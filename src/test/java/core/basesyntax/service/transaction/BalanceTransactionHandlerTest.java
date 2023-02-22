package core.basesyntax.service.transaction;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceTransactionHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static TransactionHandler handler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        handler = new BalanceTransactionHandler(storageDao);
    }

    @Test
    public void handle_successHandle_ok() {
        Integer expected = 100;
        Transaction transaction = new Transaction(Operation.BALANCE, FRUIT_NAME, 100);
        handler.handle(transaction);
        Assert.assertTrue("The storage should contain " + FRUIT_NAME, Storage.fruitStorage.containsKey(FRUIT_NAME));
        Integer actual = Storage.fruitStorage.get(FRUIT_NAME);
        Assert.assertEquals("Expected numbers of " + FRUIT_NAME + " is " + expected
                + " but actual is " + actual, expected, actual);
    }
}