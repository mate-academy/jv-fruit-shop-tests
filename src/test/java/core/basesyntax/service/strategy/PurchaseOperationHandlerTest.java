package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandler(storageDao);
    }

    @Test
    public void purchase_correct_Ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 40);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
        int expected = 8;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchase_wrongInput_NotOk() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 10);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
