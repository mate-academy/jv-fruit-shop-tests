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
    private FruitTransaction fruit;
    private FruitStorageDao dao;
    private TransactionHandler retur;

    @Before
    public void setUp() throws Exception {
        Storage.storage.put(FRUIT_NAME,FRUIT_QUANTITY);
        fruit = new FruitTransaction(FruitTransaction.Operation.RETURN,
                FRUIT_NAME,10);
        dao = new FruitStorageDaoImpl();
        retur = new ReturnTransactionHandlerImpl();
    }

    @Test
    public void addFruitName_Ok() {
        retur.transaction(fruit);
        List<String> expected = List.of(fruit.getName());
        List<String> actual = dao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = NullPointerException.class)
    public void addNullFruit_NotOk() {
        retur.transaction(null);
    }

    @Test
    public void addFruitQuantity_Ok() {
        retur.transaction(fruit);
        int actual = dao.get(fruit.getName());
        int expected = FRUIT_QUANTITY + fruit.getQuantity();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addMaxQuantityCheckName_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "grape",Integer.MAX_VALUE);
        List<String> expected = List.of(maxQuantityFruit.getName());
        retur.transaction(maxQuantityFruit);
        List<String> actual = dao.getAllFruitsNames();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void addMaxCheckQuantity_Ok() {
        FruitTransaction maxQuantityFruit = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "grape",Integer.MAX_VALUE);
        retur.transaction(maxQuantityFruit);
        int expectedInt = FRUIT_QUANTITY + maxQuantityFruit.getQuantity();
        int actualInt = dao.get(maxQuantityFruit.getName());
        Assert.assertEquals(expectedInt,actualInt);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
