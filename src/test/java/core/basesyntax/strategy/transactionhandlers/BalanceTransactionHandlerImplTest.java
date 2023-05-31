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

public class BalanceTransactionHandlerImplTest {
    private FruitTransaction fruitTransaction;
    private FruitStorageDao storageDao;
    private TransactionHandler balanceHandler;

    @Before
    public void setUp() throws Exception {
        Storage.storage.clear();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange",50);
        storageDao = new FruitStorageDaoImpl();
        balanceHandler = new BalanceTransactionHandlerImpl();
    }

    @Test
    public void transaction_FruitName_Ok() {
        balanceHandler.transaction(fruitTransaction);
        List<String> expected = List.of(fruitTransaction.getName());
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void transaction_NullFruit_NotOk() {
        balanceHandler.transaction(null);
    }

    @Test
    public void transaction_FruitQuantity_Ok() {
        balanceHandler.transaction(fruitTransaction);
        int actual = storageDao.get(fruitTransaction.getName());
        int expected = fruitTransaction.getQuantity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_FruitNameMaxquantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        List<String> expected = List.of(maxQuantityFruit.getName());
        balanceHandler.transaction(maxQuantityFruit);
        List<String> actual = storageDao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void transaction_MaxQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        balanceHandler.transaction(maxQuantityFruit);
        int expectedInt = maxQuantityFruit.getQuantity();
        int actualInt = storageDao.get(maxQuantityFruit.getName());
        Assert.assertEquals(expectedInt,actualInt);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
