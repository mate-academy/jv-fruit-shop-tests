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

public class AddOperationTest {
    private static StorageDao storageDao;
    private static OperationHandler addOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        storageDao = new StorageDaoImpl();
        addOperation = new AddOperation(storageDao);
    }

    @Before
    public void setUp() throws Exception {
        Storage.fruitStorage.clear();
    }

    @Test
    public void apply_firstTime_Ok() {
        Fruit apple = new Fruit("apple");
        addOperation.apply(new Transaction(Operation.BALANCE, apple, 10));
        int expected = 10;
        int actual = storageDao.getFruitQuantity(apple);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply_fruitAlreadyExist_Ok() {
        Fruit apple = new Fruit("apple");
        storageDao.add(apple, 100);
        addOperation.apply(new Transaction(Operation.BALANCE, apple, 10));
        int expected = 110;
        int actual = storageDao.getFruitQuantity(apple);
        Assert.assertEquals(expected, actual);
    }
}
