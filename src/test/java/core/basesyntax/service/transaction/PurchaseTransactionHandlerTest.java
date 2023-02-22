package core.basesyntax.service.transaction;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseTransactionHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static TransactionHandler handler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        handler = new PurchaseTransactionHandler(storageDao);
    }

    @Test
    public void handle_validTransaction_ok() {
        Storage.fruitStorage.put("banana", 200);
        Transaction transaction = new Transaction(Operation.PURCHASE, FRUIT_NAME, 100);
        handler.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitNotEnough_notOk() {
        Transaction transaction = new Transaction(Operation.PURCHASE, FRUIT_NAME, 100);
        handler.handle(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_fruitDoesntExist_notOk() {
        Transaction transaction = new Transaction(Operation.PURCHASE, FRUIT_NAME, 100);
        handler.handle(transaction);
    }

    @After
    public void tearDown(){
        Storage.fruitStorage.clear();
    }
}