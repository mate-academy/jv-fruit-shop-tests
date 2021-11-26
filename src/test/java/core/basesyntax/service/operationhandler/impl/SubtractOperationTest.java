package core.basesyntax.service.operationhandler.impl;

import core.basesyntax.bd.Storage;
import core.basesyntax.bd.dao.StorageDao;
import core.basesyntax.bd.dao.impl.StorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.operationhandler.Operation;
import core.basesyntax.service.operationhandler.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationTest {
    private static StorageDao storageDao;
    private static OperationHandler subtractOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        subtractOperation = new SubtractOperation(storageDao);
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitStorage.clear();
    }

    @Test (expected = RuntimeException.class)
    public void apply_firstTime_notOk() {
        Fruit apple = new Fruit("apple");
        subtractOperation.apply(new Transaction(Operation.PURCHASE, apple, 10));
        storageDao.getFruitQuantity(apple);
    }

    @Test
    public void apply_fruitAlreadyExisted_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 100);
        subtractOperation.apply(new Transaction(Operation.BALANCE, apple, 10));
        int expected = 90;
        int actual = storageDao.getFruitQuantity(apple);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void apply_subtractMoreThanStore_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 10);
        subtractOperation.apply(new Transaction(Operation.BALANCE, apple, 20));
    }
}
