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

public class ReturnTransactionHandlerImplTest {
    private static final int FRUIT_QUANTITY = 50;
    private static final String FRUIT_NAME = "grape";
    private FruitTransaction fruitTransaction;
    private FruitStorageDao storageDao;
    private TransactionHandler returnHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.clear();
        Storage.storage.put(FRUIT_NAME,FRUIT_QUANTITY);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_NAME,10);
        storageDao = new FruitStorageDaoImpl();
        returnHandler = new ReturnTransactionHandlerImpl();
    }

    @Test
    public void transaction_FruitName_Ok() {
        returnHandler.transaction(fruitTransaction);
        List<String> expected = List.of(fruitTransaction.getName());
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void transaction_NullFruit_NotOk() {
        returnHandler.transaction(null);
    }

    @Test
    public void transaction_FruitQuantity_Ok() {
        returnHandler.transaction(fruitTransaction);
        int actual = storageDao.get(fruitTransaction.getName());
        int expected = FRUIT_QUANTITY + fruitTransaction.getQuantity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_FruitNameMaxQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "grape",Integer.MAX_VALUE);
        List<String> expected = List.of(maxQuantityFruit.getName());
        returnHandler.transaction(maxQuantityFruit);
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_MaxQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "grape",Integer.MAX_VALUE);
        returnHandler.transaction(maxQuantityFruit);
        int expectedInt = FRUIT_QUANTITY + maxQuantityFruit.getQuantity();
        int actualInt = storageDao.get(maxQuantityFruit.getName());
        Assert.assertEquals(expectedInt,actualInt);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
