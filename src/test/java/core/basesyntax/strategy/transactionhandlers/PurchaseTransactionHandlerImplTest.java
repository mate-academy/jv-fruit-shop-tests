package core.basesyntax.strategy.transactionhandlers;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseTransactionHandlerImplTest {
    private static final int FRUIT_QUANTITY = 50;
    private static final String FRUIT_NAME = "banana";
    private FruitTransaction fruitTransaction;
    private FruitStorageDao storageDao;
    private TransactionHandler purchaseHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.clear();
        Storage.storage.put(FRUIT_NAME, FRUIT_QUANTITY);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                FRUIT_NAME,10);
        storageDao = new FruitStorageDaoImpl();
        purchaseHandler = new PurchaseTransactionHandlerImpl();
    }

    @Test
    public void transaction_FruitName_Ok() {
        purchaseHandler.transaction(fruitTransaction);
        List<String> expected = List.of(fruitTransaction.getName());
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void transaction_NullFruit_NotOk() {
        purchaseHandler.transaction(null);
    }

    @Test
    public void transaction_FruitQuantity_Ok() {
        purchaseHandler.transaction(fruitTransaction);
        int actual = storageDao.get(fruitTransaction.getName());
        int expected = FRUIT_QUANTITY - fruitTransaction.getQuantity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_FruitNameMaxQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",Integer.MAX_VALUE);
        List<String> expected = List.of(maxQuantityFruit.getName());
        purchaseHandler.transaction(maxQuantityFruit);
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_MaxQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana",Integer.MAX_VALUE);
        purchaseHandler.transaction(maxQuantityFruit);
        int expectedInt = FRUIT_QUANTITY - maxQuantityFruit.getQuantity();
        int actualInt = storageDao.get(maxQuantityFruit.getName());
        Assert.assertEquals(expectedInt,actualInt);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
