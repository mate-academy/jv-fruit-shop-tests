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
    private FruitTransaction fruit;
    private FruitStorageDao dao;
    private TransactionHandler balance;

    @Before
    public void setUp() throws Exception {
        fruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "orange",50);
        dao = new FruitStorageDaoImpl();
        balance = new BalanceTransactionHandlerImpl();
    }

    @Test
    public void addFruitName_Ok() {
        balance.transaction(fruit);
        List<String> expected = List.of(fruit.getName());
        List<String> actual = dao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = NullPointerException.class)
    public void addNullFruit_NotOk() {
        balance.transaction(null);
    }

    @Test
    public void addFruitQuantity_Ok() {
        balance.transaction(fruit);
        int actual = dao.get(fruit.getName());
        int expected = fruit.getQuantity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addMaxQuantityCheckName_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        List<String> expected = List.of(maxQuantityFruit.getName());
        balance.transaction(maxQuantityFruit);
        List<String> actual = dao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addMaxCheckQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple",Integer.MAX_VALUE);
        balance.transaction(maxQuantityFruit);
        int expectedInt = maxQuantityFruit.getQuantity();
        int actualInt = dao.get(maxQuantityFruit.getName());
        Assert.assertEquals(expectedInt,actualInt);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
