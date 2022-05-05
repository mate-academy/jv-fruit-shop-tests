package service.strategy;

import static org.junit.Assert.assertEquals;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.Fruit;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandler(storageDao);
    }

    @Test
    public void return_correct_ok() {
        Fruit apple = new Fruit("apple");
        Storage.dataBase.put(apple, 40);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 32);
        operationHandler.apply(fruitTransaction);
        int expected = 72;
        int actual = Storage.dataBase.get(apple);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.dataBase.clear();
    }
}
